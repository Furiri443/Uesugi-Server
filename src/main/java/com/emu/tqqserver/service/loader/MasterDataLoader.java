package com.emu.tqqserver.service.loader;

import com.emu.tqqserver.proto.pkg_pmaster.All;
import com.google.protobuf.util.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Handles the loading and merging of Master Data JSON or Binary files.
 */
public class MasterDataLoader {

    private static final Logger log = LoggerFactory.getLogger(MasterDataLoader.class);

    private final String masterDir;

    public MasterDataLoader(String masterDir) {
        this.masterDir = masterDir;
    }

    /**
     * Loads the All proto object either from binary cache or by parsing JSON files.
     */
    public All load() {
        File binFile = new File(masterDir, "master.bin");
        if (binFile.exists()) {
            return loadFromBinary(binFile);
        }

        File jsonFile = new File(masterDir, "master_full.json");
        if (jsonFile.exists()) {
            return loadFromFullJson(jsonFile, binFile);
        }

        return loadFromSplitJson();
    }

    private All loadFromBinary(File binFile) {
        try {
            log.info("MasterDataLoader: Loading master data from binary cache {}", binFile);
            byte[] data = Files.readAllBytes(binFile.toPath());
            return All.parseFrom(data);
        } catch (Exception e) {
            log.error("Failed to load master.bin", e);
            return null;
        }
    }

    private All loadFromFullJson(File jsonFile, File binFile) {
        try {
            log.info("MasterDataLoader: Loading full master data from JSON {} (this may take a while)", jsonFile);
            String json = Files.readString(jsonFile.toPath(), StandardCharsets.UTF_8);
            All.Builder builder = All.newBuilder();
            JsonFormat.parser().ignoringUnknownFields().merge(json, builder);
            All all = builder.build();

            try {
                Files.write(binFile.toPath(), all.toByteArray());
                log.info("MasterDataLoader: Saved binary cache to {}", binFile);
            } catch (Exception e) {
                log.warn("Failed to save master.bin cache", e);
            }
            return all;
        } catch (Exception e) {
            log.error("Failed to load master_full.json", e);
            return null;
        }
    }

    private All loadFromSplitJson() {
        File dir = new File(masterDir);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json") && !name.equals("master_full.json"));
        if (files == null || files.length == 0) {
            log.warn("Không tìm thấy master data tại {} — trả về All rỗng", masterDir);
            return All.getDefaultInstance();
        }

        All.Builder builder = All.newBuilder();
        JsonFormat.Parser parser = JsonFormat.parser().ignoringUnknownFields();

        int ok = 0, failed = 0;
        for (File f : files) {
            String category = f.getName().substring(0, f.getName().length() - ".json".length());
            try {
                String rawValue = Files.readString(f.toPath(), StandardCharsets.UTF_8);
                String wrapped = "{\"" + category + "\":" + rawValue + "}";
                parser.merge(wrapped, builder);
                ok++;
            } catch (Exception e) {
                log.error("Merge thất bại cho category '{}'", category, e);
                failed++;
            }
        }

        log.info("MasterDataLoader: đã nạp {}/{} category master data từ {}", ok, ok + failed, masterDir);
        return builder.build();
    }
}
