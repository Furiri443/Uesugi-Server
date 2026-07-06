package com.emu.tqqserver.service;

import com.emu.tqqserver.proto.pkg_proto.NoticeNews;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoticeService {

    private static final Logger log = LoggerFactory.getLogger(NoticeService.class);
    private static volatile NoticeService instance;

    private final List<NoticeNews> newsList;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NoticeItemJson {
        public int id;
        public int type; // category
        public int int1; // type
        public int int2; // priority
        public int int3; // banner_type
        public int assestid; // banner_id
        public String overview; // title
        public String text; // body
        public String start; // open_date
        public String end; // close_date
    }

    private NoticeService(String resourceListDir) {
        this.newsList = loadNotices(resourceListDir);
    }

    public static void initialize(String resourceListDir) {
        instance = new NoticeService(resourceListDir);
    }

    public static NoticeService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("NoticeService not initialized");
        }
        return instance;
    }

    public List<NoticeNews> getNewsList() {
        return newsList;
    }

    private List<NoticeNews> loadNotices(String resourceListDir) {
        File file = new File(resourceListDir, "notifications.json");
        List<NoticeNews> list = new ArrayList<>();
        if (!file.exists()) {
            log.warn("Notifications JSON file not found at: {} - using empty list", file.getAbsolutePath());
            return list;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            NoticeItemJson[] items = mapper.readValue(file, NoticeItemJson[].class);

            for (NoticeItemJson item : items) {
                NoticeNews.Builder builder = NoticeNews.newBuilder()
                    .setId(item.id)
                    .setCategory(item.type)
                    .setType(item.int1)
                    .setPriority(item.int2)
                    .setBannerType(item.int3)
                    .setBannerId(item.assestid)
                    .setTitle(item.overview != null ? item.overview : "お知らせ " + item.id)
                    .setBody(item.text != null ? item.text : "")
                    .setOpenDate(item.start != null ? item.start : "2020-10-27 00:00:00")
                    .setCloseDate(item.end != null ? item.end : "2030-11-30 23:59:59")
                    .setIsRead(1); // mark read by default for simple offline play
                list.add(builder.build());
            }

            log.info("Loaded {} announcements from notifications.json", list.size());

        } catch (IOException e) {
            log.error("Failed to read notifications.json", e);
        }
        return list;
    }
}
