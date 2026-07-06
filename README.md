# ごとぱず Private Server (tqq-server)

Private server implementation for **五等分の花嫁 五つ子ちゃんはパズルを五等分できない。** (Gotopazu - 5-toubun no Hanayome).

> **Game EOS**: 2025-06-30 | **Developer**: Enish Co.,Ltd. | **Internal Codename**: `Cancer`

---

## 🌟 Introduction

`tqq-server` is an effort to restore and maintain the Gotopazu game by emulating its original server. Through reverse engineering the application's source code and network protocols, this server is capable of communicating, providing master data, and saving player progress.

The project utilizes **Java 17**, **Netty** for high-performance HTTP/WebSocket handling, and **SQLite** for lightweight database storage.

## ⚙️ Implementation Status

The server has successfully implemented multiple core features enabling clients to log in and play normally:

| Feature | Status | Notes |
|-----------|--------|-------|
| **HTTP Server (Netty)** | ✅ Done | Fully routed ~250 game endpoints. |
| **Protobuf Protocol** | ✅ Done | Supports serialization and deserialization of the game's native Protobuf schemas. |
| **Auth Flow** | ✅ Done | Issues secure multi-session JWT tokens (Session Service). |
| **Player Data (DB)** | ✅ Done | Uses SQLite. Supports Profile, Cards, Units, Affection (Members), Options, etc. |
| **Master Data** | ✅ Done | Loads static master data dynamically from JSON files. |
| **JSON/Text APIs** | ✅ Done | Flexibly parses API bodies in JSON or Form-UrlEncoded formats. |
| **Friend System** | ✅ Done | Send requests, Approve, Reject, Delete friends (integrated with JSON). |
| **CDN (Assets)** | ✅ Done | Serves Asset files directly over the Server port, supports ETag & 304. |
| **Puzzle (Minigame)** | 🔧 WIP | Score calculation and post-clear rewards logic. |
| **Gacha** | 🔧 WIP | Algorithms and drop rates. |

---

## 🛠 Setup & Run Instructions

### 1. Requirements
- **Java 17** or higher.
- **Maven** (to build the project).
- Directories containing Master data and CDN assets (extracted from the client).

### 2. Build the Project
```bash
cd tqq-server
mvn clean package -DskipTests
```

### 3. Run the Server
The server runs all services (API, WebSocket, CDN) on a single port.
```bash
java -jar target/tqq-server-1.0.0-SNAPSHOT.jar \
  --port 8080 \
  --db ./gotopazu.db \
  --cdn-dir /path/to/assets_cdn \
  --resource-dir /path/to/gotopazu
```

Alternatively, use environment variables:
```bash
GOTOPAZU_PORT=8080 \
GOTOPAZU_CDN_DIR=/path/to/assets_cdn \
GOTOPAZU_RESOURCE_DIR=/path/to/gotopazu \
  java -jar target/tqq-server-1.0.0-SNAPSHOT.jar
```

---

## 📂 Code Structure

```text
tqq-server/
├── pom.xml                 # Maven configuration
├── docs/                   # Reverse engineering docs (Il2Cpp, Protobuf schemas)
├── tools/                  # Python scripts for patching the client
└── src/main/
    ├── proto/              # Protobuf definitions (.proto) for client & server
    ├── resources/          # Default configurations (config.json)
    └── java/com/emu/tqqserver/
        ├── server/         # Netty Server bootstrap
        ├── network/        # HTTP/WebSocket handlers, Routing, CDN, API Decoder
        ├── game/           # Game logic separated by features (user, friend, unit, collection...)
        ├── db/             # SQLite Database connections and Data Access Objects (Dao)
        └── model/          # Entity class definitions for the database
```

## 📝 Additional Information (Reverse Engineering)

Original Game Protocol:
- **HTTP POST** with `Content-Type: application/x-protobuf`.
- **Required Headers**:
  - `X-Enish-App-Session`: Authorization token issued by the `/account/certificate` API.
  - `X-Enish-App-Version`: Application version (e.g., 1.43.440).
  - `X-Enish-Date`, `X-Enish-Expiredate`: Unix timestamps.
- Responses are wrapped in standard structures including `Master`, `Nocontent`, or `Error`. The server accurately simulates this structure.
