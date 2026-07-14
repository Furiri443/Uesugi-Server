package com.emu.tqqserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import com.emu.tqqserver.db.DatabaseManager;
import com.emu.tqqserver.network.websocket.GameServerInitializer;
import com.emu.tqqserver.game.master.MasterDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point — chạy trên một port duy nhất.
 *
 * <p>Tất cả traffic đều qua cùng port (mặc định 8080):
 * <ul>
 *   <li>{@code POST /account/*, /user/*, /puzzle/*, …}        → API handlers</li>
 *   <li>{@code POST /resource/list/{Android|Ios}}             → Resource list (proto)</li>
 *   <li>{@code GET  /assets-cancer/Resources/{p}/{hash}}      → CDN serve từ assets_cdn/</li>
 *   <li>{@code GET  /ws} (Upgrade: websocket)                 → GameWebSocketHandler</li>
 *   <li>{@code GET  /health}                                  → health check</li>
 * </ul>
 *
 * <p><b>Discovered server URLs</b>:
 * <ul>
 *   <li>API:    {@code https://www-cancer.enish-games.com}  → port 8080</li>
 *   <li>Assets: {@code https://assets.enish-games.com}      → port 8080 (same)</li>
 *   <li>WS RT:  {@code ws://rt-cancer.enish-games.com}      → port 8080 (same, /ws)</li>
 * </ul>
 */
public class GotopazuServer {

    private static final Logger log = LoggerFactory.getLogger(GotopazuServer.class);

    public static final String GAME_CODENAME = "cancer";
    public static final int    DEFAULT_PORT   = 8080;

    private final ServerConfig config;

    public GotopazuServer(ServerConfig config) {
        this.config = config;
    }

    public void start() throws InterruptedException {
        com.emu.tqqserver.game.GameContext.getInstance().setConfig(this.config);

        System.setProperty("gotopazu.resource.list.dir", config.getResourceListDir());
        System.setProperty("gotopazu.cdn.dir", config.getCdnDir());
        System.setProperty("gotopazu.asset.url.prefix", config.getAssetUrlPrefix());
        log.info("Loaded server configuration.");

        // Load puzzle stage data
        com.emu.tqqserver.data.ResourceLoader.setResourceDir(config.getResourceListDir());
        com.emu.tqqserver.data.ResourceLoader.loadAll();

        // 2. Initialize Database
        java.io.File sqliteTmp = new java.io.File(config.getSqliteTmpDir());
        if (!sqliteTmp.exists()) {
            sqliteTmp.mkdirs();
        }
        System.setProperty("org.sqlite.tmpdir", sqliteTmp.getAbsolutePath());

        DatabaseManager.getInstance().initialize(config.getDbPath());
        MasterDataService.initialize(config.getResourceListDir());
        
        log.info("Pre-loading master data...");
        long startMs = System.currentTimeMillis();
        MasterDataService.getInstance().getAllBytes();
        log.info("Pre-loaded master data in {} ms", System.currentTimeMillis() - startMs);

        com.emu.tqqserver.game.shop.IAPProductService.initialize(config.getResourceListDir());
        com.emu.tqqserver.game.notice.NoticeService.initialize(config.getResourceListDir());

        com.emu.tqqserver.console.ConsoleCommandManager.startConsoleThread();

        EventLoopGroup boss   = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ChannelFuture future = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new GameServerInitializer(config))
                .option(ChannelOption.SO_BACKLOG, 256)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .bind(config.getHost(), config.getPort())
                .sync();

            log.info("━━━ ごとぱず private server ━━━");
            log.info("Host    : {}", config.getHost());
            log.info("Port    : {}", config.getPort());
            log.info("CDN dir : {}", config.getCdnDir());
            log.info("Res dir : {}", config.getResourceListDir());
            log.info("DB      : {}", config.getDbPath());
            log.info("Endpoints: API + CDN + WebSocket on same port");

            future.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
            DatabaseManager.getInstance().close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GotopazuServer(ServerConfig.fromArgs(args)).start();
    }
}
