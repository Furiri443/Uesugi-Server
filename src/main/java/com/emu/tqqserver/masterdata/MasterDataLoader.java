package com.emu.tqqserver.masterdata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class MasterDataLoader {
    private static final Logger log = LoggerFactory.getLogger(MasterDataLoader.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    
    // Cache for loaded JSON nodes
    private static final Map<String, JsonNode> cache = new HashMap<>();
    private static String masterDataDir = "gotopazu/master";

    public static void setMasterDataDir(String dir) {
        masterDataDir = dir;
    }

    /**
     * Load a master data JSON file. Example: "chapter.json"
     * Returns the root JsonNode, or an empty object node if not found/error.
     */
    public static JsonNode loadJson(String filename) {
        if (cache.containsKey(filename)) {
            return cache.get(filename);
        }

        File file = new File(masterDataDir, filename);
        if (!file.exists()) {
            log.warn("Master data file not found: {}", file.getAbsolutePath());
            return mapper.createObjectNode();
        }

        try {
            JsonNode node = mapper.readTree(file);
            cache.put(filename, node);
            return node;
        } catch (Exception e) {
            log.error("Failed to parse master data file: {}", filename, e);
            return mapper.createObjectNode();
        }
    }
    
    /**
     * Get a list of JsonNodes from the root JSON (assuming the root is a list, or the objects are nested).
     * Typically gotopazu JSONs are arrays of objects.
     */
    public static List<JsonNode> getList(String filename) {
        JsonNode root = loadJson(filename);
        if (root.isArray()) {
            List<JsonNode> list = new ArrayList<>();
            for (JsonNode element : root) {
                list.add(element);
            }
            return list;
        }
        return Collections.emptyList();
    }
}
