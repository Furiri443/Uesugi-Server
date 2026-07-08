package com.emu.tqqserver.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static final Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy hh:mm:ss").setPrettyPrinting().create();
    private static final Gson gsonCompact = new GsonBuilder().create();

    public static Gson getGsonFactory() {
        return gson;
    }

    public static String encode(Object object) {
        return gson.toJson(object);
    }

    public static String encode(Object object, boolean compact) {
        return compact ? gsonCompact.toJson(object) : gson.toJson(object);
    }

    public static JsonElement encodeToElement(Object object) {
        return gson.toJsonTree(object);
    }

    public static <T> T decode(String jsonData, Class<T> classType) {
        if (jsonData == null) {
            return null;
        }
        try {
            return gson.fromJson(jsonData, classType);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static <T> List<T> decodeList(String jsonData, Class<T> classType) {
        if (jsonData == null) return null;
        try {
            return gson.fromJson(jsonData, TypeToken.getParameterized(List.class, classType).getType());
        } catch (Exception e) {
            System.err.println("Error decoding list for " + classType.getName());
            e.printStackTrace();
            return null;
        }
    }

    public static <T> Set<T> decodeSet(String jsonData, Class<T> classType) {
        if (jsonData == null) return null;
        try {
            return gson.fromJson(jsonData, TypeToken.getParameterized(Set.class, classType).getType());
        } catch (Exception ignored) {
            return null;
        }
    }

    public static <T1, T2> Map<T1, T2> decodeMap(String jsonData, Class<T1> keyType, Class<T2> valueType) {
        return gson.fromJson(jsonData, TypeToken.getParameterized(LinkedHashMap.class, keyType, valueType).getType());
    }

    public static <T> T loadToClass(InputStreamReader fileReader, Class<T> classType) throws IOException {
        return gson.fromJson(fileReader, classType);
    }

    public static <T> T loadToClass(File file, Class<T> classType) throws IOException {
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            return loadToClass(fileReader, classType);
        }
    }

    public static <T> List<T> loadToList(InputStreamReader fileReader, Class<T> classType) throws IOException {
        return gson.fromJson(fileReader, TypeToken.getParameterized(List.class, classType).getType());
    }

    public static <T> List<T> loadToList(String filename, Class<T> classType) throws IOException {
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8)) {
            return loadToList(fileReader, classType);
        }
    }

    public static <T1, T2> Map<T1, T2> loadToMap(InputStreamReader fileReader, Class<T1> keyType, Class<T2> valueType) throws IOException {
        return gson.fromJson(fileReader, TypeToken.getParameterized(LinkedHashMap.class, keyType, valueType).getType());
    }

    public static <T1, T2> Map<T1, T2> loadToMap(String filename, Class<T1> keyType, Class<T2> valueType) throws IOException {
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8)) {
            return loadToMap(fileReader, keyType, valueType);
        } catch (FileNotFoundException ignored) {
            log.error("File not found: {}.", filename);
            return null;
        }
    }
}
