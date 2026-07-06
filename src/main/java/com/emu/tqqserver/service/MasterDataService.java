package com.emu.tqqserver.service;

import com.emu.tqqserver.proto.pkg_pmaster.All;
import com.emu.tqqserver.service.loader.MasterDataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Caches and serves Master Data for the game.
 * Delegates the actual loading from disk to MasterDataLoader.
 */
public class MasterDataService {

    private static final Logger log = LoggerFactory.getLogger(MasterDataService.class);
    private static volatile MasterDataService instance;

    private final MasterDataLoader masterDataLoader;
    private final AtomicReference<All> cachedAll = new AtomicReference<>();
    private final AtomicReference<byte[]> cachedAllBytes = new AtomicReference<>();

    private MasterDataService(String resourceListDir) {
        String masterDir = resourceListDir + File.separator + "master";
        this.masterDataLoader = new MasterDataLoader(masterDir);
    }

    /** Khởi tạo singleton — gọi một lần lúc server start. */
    public static void initialize(String resourceListDir) {
        instance = new MasterDataService(resourceListDir);
    }

    public static MasterDataService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("MasterDataService chưa được initialize()");
        }
        return instance;
    }

    /** Trả về {@code pkg_pmaster.All} đầy đủ, build 1 lần rồi cache. */
    public synchronized All getAll() {
        All existing = cachedAll.get();
        if (existing != null) {
            return existing;
        }
        All built = masterDataLoader.load();
        if (built == null) {
            built = All.getDefaultInstance();
        }
        cachedAll.set(built);
        return built;
    }

    /** Trả về bytes proto binary của {@code All}, cache sẵn để phục vụ nhanh. */
    public synchronized byte[] getAllBytes() {
        byte[] existing = cachedAllBytes.get();
        if (existing != null) {
            return existing;
        }
        byte[] bytes = getAll().toByteArray();
        cachedAllBytes.set(bytes);
        return bytes;
    }
}
