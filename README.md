# ごとぱず Private Server (tqq-server)

Private server reimplementation for **五等分の花嫁 五つ子ちゃんはパズルを五等分できない。** (Gotopazu)

> Game EOS: 2025-06-30 | Developer: Enish Co.,Ltd. | Internal codename: `Cancer`

---

## Discovered Server Info (from Il2CppDumper + binary analysis)

| Role | Original URL |
|------|-------------|
| **HTTP API** | `https://www-cancer.enish-games.com` |
| **Asset CDN** | `https://assets.enish-games.com/assets-cancer/Resources` |
| **Realtime WS** | `ws://rt-cancer.enish-games.com` |
| **Firebase** | `https://cancer-production.firebaseio.com` |

**Protocol**: HTTP POST, `Content-Type: application/x-protobuf` (protobuf-net .NET format)

**Required Headers**:
```
X-Enish-App-Language          ja
X-Enish-App-Platform          android / ios
X-Enish-App-Session           <token from /account/certificate>
X-Enish-App-Version           1.43.440
X-Enish-App-Version-Master    <master data version>
X-Enish-App-Version-Resource  <resource version>
X-Enish-Date                  <unix timestamp>
X-Enish-Expiredate            <expiry timestamp>
X-Enish-App-Store             google / apple
X-Enish-App-Review            0
X-Enish-App-Season-ID         <current season id>
X-Enish-App-Resource-Cnt      <resource download count>
X-Enish-App-User-Agent        <app user agent>
```

---

## Proto Schema

The game uses **protobuf-net** (C# .NET protobuf library). Full schema extracted from:
- `proto.dll` → `docs/proto_dump_full.cs` (75,736 lines of IL2CPP dump)
- `proto_serializer.dll` → `docs/proto_serializer_dump.cs`

Every response wraps in one of:
```protobuf
Master    { StoredData stored_data = 1; All all = 2; }       // full data
Error     { StoredData stored_data = 1; ErrorDetail error = 2; }
Nocontent { StoredData stored_data = 1; }                     // 204-like
```

`StoredData` contains the player state delta (only changed fields sent).
See `src/main/proto/gotopazu_game.proto` for the complete schema (940+ lines).

---

## Auth Flow

```
1. POST /account/certificate
   Body: AccountCertificateRequest { token, platform, device_id, adid }
   Resp: Cert { token, version }       ← save token for X-Enish-App-Session

2. POST /account/authorize
   Header: X-Enish-App-Session = <token>
   Resp: Master { stored_data, all }   ← full player + master data
```

---

## iOS Patching

### Step 1: Patch resources.assets (URL redirect)
```bash
python3 tools/patch_ios_resources.py \
  /Volumes/SoftwareDisk/Payload/ProductName.app/Data/resources.assets \
  192.168.1.100 \
  patched_resources.assets \
  8080 8081 8082
```
Patches (same-length byte replacement, Unity-safe):
- `https://www-cancer.enish-games.com` → `http://IP:8080/...`
- `https://assets.enish-games.com/assets-cancer/Resources` → `http://IP:8081/...`
- `ws://rt-cancer.enish-games.com` → `ws://IP:8082/...`

### Step 2: Patch UnityFramework (IAP bypass)
```bash
python3 tools/patch_ios_unityframework.py \
  /Volumes/SoftwareDisk/Payload/ProductName.app/Frameworks/UnityFramework.framework/UnityFramework
```
Applies: `iap_manager_update_product_list_bypass` @ 0x26751C0

### Step 3: Resign & deploy
```bash
codesign -f -s - /Volumes/SoftwareDisk/Payload/ProductName.app
```

---

## Asset CDN

Files in `/Volumes/OCungRoi/PRJ/GTPZ-PS/assets_cdn/`:
- **54,054 files**, ~9.3 GB total
- Named by 32-char MD5 hash (content-addressed)
- Android coverage: 27,855/29,978 (92.9%)
- iOS coverage: 13/30,015 (0.04%) — iOS bundles likely need separate download

Resource list proto files (capture from production):
- `/Volumes/OCungRoi/PRJ/GTPZ-PS/gotopazu/Android` — 29,978 entries
- `/Volumes/OCungRoi/PRJ/GTPZ-PS/gotopazu/Ios` — 30,015 entries

Proto entry format:
```
repeated {
  int64  id   = 1;   // bundle id
  msg {
    string name = 1;  // 32-char hex hash = filename in assets_cdn/
    int64  size = 2;  // file size in bytes
  } = 2;
}
```

CDN URL flow:
1. Client POST `/resource/list/Android` → server returns `gotopazu/Android` proto
2. Client reads each `{hash, size}` entry
3. Client GET `http://CDN_HOST:8081/assets-cancer/Resources/android/{hash}`
4. Server looks up `assets_cdn/{hash}` and streams it

---

## Architecture

```
Client (Android / iOS)  ─── tất cả qua cùng port :8080 ───────────────────────────
  │
  ├─ POST /account/*, /user/*, /puzzle/*, … ─► HttpApiHandler (248 endpoints)
  │  Content-Type: application/x-protobuf         ├─ AccountRoutes, UserRoutes, …
  │  (was https://www-cancer.enish-games.com)      └─ ResourceRoutes /resource/list/{Android|Ios}
  │                                                    → serve gotopazu/Android proto trực tiếp
  │
  ├─ GET /assets-cancer/Resources/{p}/{hash}──► HttpApiHandler (CDN path)
  │  (was https://assets.enish-games.com)           → stream assets_cdn/{hash} qua ChunkedFile
  │                                                    ETag=hash, 304 nếu client đã có
  │
  └─ GET /ws  (Upgrade: websocket) ──────────► GameWebSocketHandler
     (was ws://rt-cancer.enish-games.com)          real-time puzzle events

Pipeline (GameServerInitializer):
  HttpServerCodec → HttpObjectAggregator(8MB) → ChunkedWriteHandler
  → WebSocketServerCompressionHandler → WebSocketServerProtocolHandler(/ws)
  → HttpApiHandler → GameWebSocketHandler
```

---

## Build & Run

```bash
# Build fat JAR
cd tqq-server
mvn package -DskipTests

# Run — API + CDN + WS cùng port 8080
java -jar target/tqq-server-1.0.0-SNAPSHOT.jar

# Custom port / paths
java -jar target/tqq-server-1.0.0-SNAPSHOT.jar \
  --port 8080 \
  --db ./gotopazu.db \
  --cdn-dir /Volumes/OCungRoi/PRJ/GTPZ-PS/assets_cdn \
  --resource-dir /Volumes/OCungRoi/PRJ/GTPZ-PS/gotopazu

# Env vars
GOTOPAZU_PORT=8080 \
GOTOPAZU_CDN_DIR=/Volumes/OCungRoi/PRJ/GTPZ-PS/assets_cdn \
GOTOPAZU_RESOURCE_DIR=/Volumes/OCungRoi/PRJ/GTPZ-PS/gotopazu \
  java -jar target/tqq-server-*.jar
```

### Verify sau khi chạy
```bash
# Health check
curl http://localhost:8080/health

# CDN — nên trả asset bundle bytes (200 + Content-Length)
curl -I http://localhost:8080/assets-cancer/Resources/android/2003e7d515ec2d5f4f379e3dfaf26de5

# CDN ETag / 304 check
curl -I -H 'If-None-Match: "2003e7d515ec2d5f4f379e3dfaf26de5"' \
  http://localhost:8080/assets-cancer/Resources/android/2003e7d515ec2d5f4f379e3dfaf26de5

# Resource list (raw proto, ~1.4MB)
curl -s -X POST http://localhost:8080/resource/list/Android \
  -H "X-Enish-App-Session: test" \
  -H "X-Enish-App-Version: 1.43.440" \
  | wc -c   # expect ~1406159 bytes
```

### iOS patch (một port cho tất cả)
```bash
python3 tools/patch_ios_resources.py \
  /Volumes/SoftwareDisk/Payload/ProductName.app/Data/resources.assets \
  192.168.1.100 \
  patched_resources.assets \
  8080    # single port — API + CDN + WS
```

---

## Implementation Status

| Component | Status | Notes |
|-----------|--------|-------|
| HTTP server (Netty) | ✅ | All 248 endpoints registered |
| Proto schema | ✅ | Full schema from Il2CppDumper |
| Auth flow | 🔧 Stub | Returns valid Cert token |
| StoredData delta | ⏳ | Need proto-net compatible encoder |
| Master data (`/master/all`) | ⏳ | Need to extract from binary |
| Resource list (`/resource/list/Android`) | ✅ | Serves `gotopazu/Android` proto directly |
| Resource list (`/resource/list/Ios`) | ✅ | Serves `gotopazu/Ios` proto directly |
| Asset CDN (`:8081 /assets-cancer/Resources/*`) | ✅ | Serves `assets_cdn/{hash}` flat files, ETag+304 |
| Puzzle (start/clear/fail) | 🔧 Stub | Score/reward logic TODO |
| Gacha | 🔧 Stub | Algorithm + rates TODO |
| SQLite DB | ✅ | Schema: 7 tables |
| iOS patch (resources.assets) | ✅ | `tools/patch_ios_resources.py` |
| iOS patch (UnityFramework) | ✅ | `tools/patch_ios_unityframework.py` |
| WebSocket realtime | 🔧 Stub | Frame format TBD |

**Legend**: ✅ Done | 🔧 Stub | ⏳ Not started

---

## Key Files

```
tqq-server/
├── pom.xml
├── docs/
│   ├── proto_dump_full.cs          ← Il2CppDumper output (proto.dll, 75k lines)
│   ├── proto_serializer_dump.cs    ← proto_serializer.dll dump
│   ├── REVERSE_ENGINEERING_NOTES.md
│   └── iOS_PATCHING.md
├── tools/
│   ├── dump_cs_to_proto.py         ← Convert dump.cs → .proto
│   ├── gen_route_stubs.py          ← Generate Java route stubs
│   ├── patch_ios_resources.py      ← iOS resources.assets URL patcher
│   └── patch_ios_unityframework.py ← iOS UnityFramework IAP bypass
└── src/main/
    ├── proto/
    │   ├── gotopazu_game.proto     ← Full game proto schema (940 lines)
    │   └── gotopazu.proto          ← Server-internal protos
    └── java/jp/enish/gotopazu/
        ├── server/                 GotopazuServer, ServerConfig
        ├── network/
        │   ├── http/               HttpApiHandler + all Route classes
        │   └── websocket/          GameWebSocketHandler, GameSession
        ├── handler/                PacketDispatcher + command handlers
        ├── service/                UserService, SessionService
        └── db/                     DatabaseManager (SQLite)
```
