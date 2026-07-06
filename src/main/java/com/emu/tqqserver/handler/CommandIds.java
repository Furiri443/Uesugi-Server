package com.emu.tqqserver.handler;

/**
 * Command ID constants for the Gotopazu game protocol.
 *
 * <p>These IDs correspond to the message type enum in proto.dll / proto_serializer.dll.
 * Values here are PLACEHOLDERS and must be updated after full reverse engineering
 * of the binary (global-metadata.dat + libil2cpp.so analysis via IDA).
 *
 * <p>Naming convention from game namespace (Cancer.*):
 * - Request packets sent by client use even IDs (0x0002, 0x0004, ...)
 * - Response packets sent by server echo the same ID
 * - Push notifications from server use a separate range (0x8000+)
 *
 * TODO: Replace all 0xXXXX with actual values from IDA decompilation.
 *       Run Il2CppDumper on global-metadata.dat to get full enum.
 */
public final class CommandIds {

    private CommandIds() {}

    // ---- Authentication (0x0001 - 0x000F) ----
    /** ゲストログイン (Guest login without account) */
    public static final int AUTH_GUEST_LOGIN = 0x0001;
    /** ログイン (Login with platform account: Google/Apple/Guest) */
    public static final int AUTH_LOGIN       = 0x0002;
    /** ログアウト */
    public static final int AUTH_LOGOUT      = 0x0003;

    // ---- Home / Title (0x0010 - 0x001F) ----
    /** ホーム取得 */
    public static final int HOME_GET         = 0x0010;

    // ---- User (0x0020 - 0x002F) ----
    /** プロフィール取得 */
    public static final int USER_GET_PROFILE = 0x0020;
    /** プロフィール更新 */
    public static final int USER_SET_PROFILE = 0x0021;
    /** チュートリアル完了 */
    public static final int TUTORIAL_COMPLETE = 0x0022;

    // ---- Master data (0x0030 - 0x003F) ----
    /** マスターデータ取得 */
    public static final int MASTER_GET       = 0x0030;
    /** AssetBundle カタログ取得 */
    public static final int ASSET_CATALOG    = 0x0031;

    // ---- Gacha (0x0040 - 0x004F) ----
    /** ガチャトップ */
    public static final int GACHA_TOP        = 0x0040;
    /** ガチャ実行 */
    public static final int GACHA_EXEC       = 0x0041;

    // ---- Stage / Puzzle (0x0050 - 0x005F) ----
    /** ステージトップ */
    public static final int STAGE_TOP        = 0x0050;
    /** ステージ開始 */
    public static final int STAGE_START      = 0x0051;
    /** ステージクリア */
    public static final int STAGE_CLEAR      = 0x0052;
    /** ステージ中断 */
    public static final int STAGE_RETIRE     = 0x0053;

    // ---- Story (0x0060 - 0x006F) ----
    /** ストーリー一覧 */
    public static final int STORY_GET        = 0x0060;
    /** ストーリー解放 */
    public static final int STORY_UNLOCK     = 0x0061;

    // ---- Shop (0x0070 - 0x007F) ----
    /** ショップトップ */
    public static final int SHOP_TOP         = 0x0070;
    /** アイテム購入 */
    public static final int SHOP_BUY         = 0x0071;
    /** 課金購入 */
    public static final int SHOP_IAP         = 0x0072;

    // ---- Stamina (0x0080 - 0x008F) ----
    /** スタミナ回復 */
    public static final int STAMINA_RECOVER  = 0x0080;

    // ---- Friend (0x0090 - 0x009F) ----
    /** フレンド一覧 */
    public static final int FRIEND_LIST      = 0x0090;
    /** フレンド申請 */
    public static final int FRIEND_REQUEST   = 0x0091;
    /** フレンド承認 */
    public static final int FRIEND_ACCEPT    = 0x0092;

    // ---- Notice / Event (0x00A0 - 0x00AF) ----
    /** お知らせ一覧 */
    public static final int NOTICE_LIST      = 0x00A0;
    /** イベント情報 */
    public static final int EVENT_INFO       = 0x00A1;

    // ---- Server Push (0x8000+) ----
    /** ログインボーナス配信 */
    public static final int PUSH_LOGIN_BONUS = 0x8001;
    /** メンテナンス通知 */
    public static final int PUSH_MAINTENANCE = 0x8002;
}
