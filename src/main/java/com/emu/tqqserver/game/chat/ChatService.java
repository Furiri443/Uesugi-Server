package com.emu.tqqserver.game.chat;

import com.emu.tqqserver.network.websocket.GameSession;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import java.util.Map;
import java.util.Collections;

public class ChatService {
    private static final ChatService instance = new ChatService();

    // Map channelName -> Set of GameSessions
    private final Map<String, Set<GameSession>> channelSubscribers = new ConcurrentHashMap<>();

    // Map channelName -> List of ChatMessages
    private final Map<String, java.util.List<com.emu.tqqserver.proto.pkg_prealtime.ChatMessage>> channelHistory = new ConcurrentHashMap<>();

    private ChatService() {}

    public static ChatService getInstance() {
        return instance;
    }

    public void addMessage(String channel, com.emu.tqqserver.proto.pkg_prealtime.ChatMessage msg) {
        channelHistory.computeIfAbsent(channel, k -> java.util.Collections.synchronizedList(new java.util.ArrayList<>())).add(msg);
        
        byte[] payload = msg.toByteArray();
        
        Set<GameSession> subs = channelSubscribers.get(channel);
        if (subs != null) {
            for (GameSession s : subs) {
                s.sendPush(12, payload);
            }
        }
    }

    public java.util.List<com.emu.tqqserver.proto.pkg_prealtime.ChatMessage> getHistory(String channel) {
        return channelHistory.getOrDefault(channel, Collections.emptyList());
    }

    public void subscribe(String channel, GameSession session) {
        channelSubscribers.computeIfAbsent(channel, k -> ConcurrentHashMap.newKeySet()).add(session);
    }

    public void unsubscribe(String channel, GameSession session) {
        Set<GameSession> subs = channelSubscribers.get(channel);
        if (subs != null) {
            subs.remove(session);
        }
    }

    public void unsubscribeAll(GameSession session) {
        for (Set<GameSession> subs : channelSubscribers.values()) {
            subs.remove(session);
        }
    }

    public int getSubscriberCount(String channel) {
        Set<GameSession> subs = channelSubscribers.get(channel);
        return subs == null ? 0 : subs.size();
    }
}
