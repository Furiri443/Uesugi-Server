package com.emu.tqqserver.data;

import com.emu.tqqserver.proto.pkg_pmaster.All;
import com.emu.tqqserver.utils.JsonUtils;
import com.google.protobuf.util.JsonFormat;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResourceLoader {
    private static final Logger log = LoggerFactory.getLogger(ResourceLoader.class);
    private static boolean loaded = false;
    private static String resourceDir = "gotopazu";
    private static All masterDataAll = null;

    public static void loadAll() {
        if (loaded) return;

        loadResources();

        loaded = true;
        log.info("Resource loading complete");
    }

    public static void setResourceDir(String dir) {
        resourceDir = dir;
    }

    public static All getMasterDataAll() {
        if (!loaded) {
            loadAll();
        }
        return masterDataAll != null ? masterDataAll : All.getDefaultInstance();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void loadResources() {
        // Map filename -> ResourceType Class
        List<Class<?>> classes = new Reflections("com.emu.tqqserver.data.resources")
                .getTypesAnnotatedWith(ResourceType.class)
                .stream()
                .collect(Collectors.toList());

        Map<String, Class<?>> fileToClassMap = new HashMap<>();
        for (Class<?> def : classes) {
            ResourceType type = def.getAnnotation(ResourceType.class);
            if (type != null) {
                fileToClassMap.put(type.name(), def);
            }
        }

        File masterDir = new File(resourceDir, "master");
        if (!masterDir.exists() || !masterDir.isDirectory()) {
            log.warn("Master directory {} does not exist!", masterDir.getAbsolutePath());
            return;
        }

        File[] jsonFiles = masterDir.listFiles((d, name) -> name.endsWith(".json") && !name.equals("master_full.json"));
        if (jsonFiles == null || jsonFiles.length == 0) {
            log.warn("No master JSON files found in {}.", masterDir.getAbsolutePath());
            masterDataAll = All.getDefaultInstance();
            return;
        }

        All.Builder allBuilder = All.newBuilder();
        JsonFormat.Parser parser = JsonFormat.parser().ignoringUnknownFields();

        int loadedProtoCount = 0;
        int failedProtoCount = 0;

        for (File f : jsonFiles) {
            String fileName = f.getName();
            String category = fileName.substring(0, fileName.length() - ".json".length());

            try {
                String jsonContent = Files.readString(f.toPath(), StandardCharsets.UTF_8);

                // Parse into GameData using the Def class
                Class<?> resourceClass = fileToClassMap.get(fileName);
                if (resourceClass != null) {
                    DataTable<?> table = getTableForResource(GameData.class, (Class<? extends BaseDef>) resourceClass);
                    Iterable<?> defs = JsonUtils.decodeList(jsonContent, resourceClass);
                    
                    int count = 0;
                    if (defs != null) {
                        for (Object o : defs) {
                            BaseDef res = (BaseDef) o;
                            if (res == null) continue;
                            res.onLoad();
                            if (table != null) {
                                ((DataTable<BaseDef>) table).add(res);
                            }
                            count++;
                        }
                    }
                    log.info("Loaded {} {}s from {}.", count, resourceClass.getSimpleName(), fileName);

                    // Re-encode from GameData table and merge to Protobuf All.Builder in chunks to save memory
                    if (table != null && table.values() != null) {
                        java.util.List<?> list = new java.util.ArrayList<>(table.values());
                        int chunkSize = 2000;
                        for (int i = 0; i < list.size(); i += chunkSize) {
                            int end = Math.min(list.size(), i + chunkSize);
                            java.util.List<?> chunk = list.subList(i, end);
                            String reencodedJson = JsonUtils.encode(chunk, true);
                            String wrapped = "{\"" + category + "\":" + reencodedJson + "}";
                            parser.merge(wrapped, allBuilder);
                        }
                        loadedProtoCount++;
                    }
                } else if (fileName.equals("puzzle.json") || fileName.equals("reaction.json") || fileName.equals("text.json") || fileName.equals("adventure_resource.json")) {
                    String wrapped = "{\"" + category + "\":" + jsonContent + "}";
                    JsonFormat.parser().ignoringUnknownFields().merge(wrapped, allBuilder);
                    log.info("Merged {} directly to Protobuf", fileName);
                } else {
                    log.warn("No Def class mapped for {}", fileName);
                }

            } catch (Exception e) {
                log.error("Failed to load/merge category '{}' from {}", category, fileName, e);
                failedProtoCount++;
            }
        }

        masterDataAll = allBuilder.build();
        log.info("MasterData loader finished: merged {} proto categories ({} failed).", loadedProtoCount, failedProtoCount);
    }

    @SuppressWarnings("unchecked")
    private static <T extends BaseDef> DataTable<T> getTableForResource(Class<?> dataClass, Class<T> resourceClass) {
        DataTable<T> table = null;
        Field field = null;

        String simpleName = resourceClass.getSimpleName();
        if (simpleName.endsWith("Def")) {
            simpleName = simpleName.substring(0, simpleName.length() - 3) + "Data";
        }

        try {
            field = dataClass.getDeclaredField(simpleName + "Table");
        } catch (Exception e) {
            try {
                field = dataClass.getDeclaredField(lowerCaseFirstChar(simpleName) + "Table");
            } catch (Exception ex) {
                // Ignore
            }
        }

        if (field != null) {
            try {
                field.setAccessible(true);
                table = (DataTable<T>) field.get(null);
            } catch (Exception e) {
                // ignore exception
            } finally {
                field.setAccessible(false);
            }
        }

        return table;
    }

    private static String lowerCaseFirstChar(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
