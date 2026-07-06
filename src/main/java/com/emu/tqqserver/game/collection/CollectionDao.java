package com.emu.tqqserver.game.collection;

import com.emu.tqqserver.db.DatabaseManager;
import com.emu.tqqserver.proto.pkg_proto.CollectionPage;
import com.emu.tqqserver.proto.pkg_proto.CollectionElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectionDao {
    private static final Logger log = LoggerFactory.getLogger(CollectionDao.class);
    
    private static class UserCollection {
        public int bgmId;
        public int viewCount;
    }

    public com.emu.tqqserver.proto.pkg_puser.Collection getCollection(long userId) {
        String sql = "SELECT bgm_id, view_count FROM user_collections WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return com.emu.tqqserver.proto.pkg_puser.Collection.newBuilder()
                            .setUid((int) userId)
                            .setBgmId(rs.getInt("bgm_id"))
                            .setViewCount(rs.getInt("view_count"))
                            .build();
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get collection for user {}", userId, e);
        }
        
        // Return default
        return com.emu.tqqserver.proto.pkg_puser.Collection.newBuilder()
                .setUid((int) userId)
                .setBgmId(1)
                .setViewCount(0)
                .build();
    }

    public void updateCollection(long userId, int bgmId) {
        String sql = "INSERT INTO user_collections (user_id, bgm_id, view_count) VALUES (?, ?, 0) " +
                     "ON CONFLICT(user_id) DO UPDATE SET bgm_id = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, bgmId);
            ps.setInt(3, bgmId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Failed to update collection for user {}", userId, e);
        }
    }

    public List<CollectionPage> getCollectionPages(long userId) {
        List<CollectionPage> pages = new ArrayList<>();
        String sql = "SELECT idx, title, layout_id, theme_id, transition_id, page_sort FROM user_collection_pages WHERE user_id = ? ORDER BY idx ASC";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CollectionPage.Builder page = CollectionPage.newBuilder()
                            .setUid((int) userId)
                            .setIdx(rs.getInt("idx"))
                            .setTitle(rs.getString("title") != null ? rs.getString("title") : "")
                            .setLayoutId(rs.getInt("layout_id"))
                            .setThemeId(rs.getInt("theme_id"))
                            .setTransitionId(rs.getInt("transition_id"))
                            .setPageSort(rs.getInt("page_sort"));
                    
                    page.addAllCollectionElements(getCollectionElements(conn, userId, rs.getInt("idx")));
                    pages.add(page.build());
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get collection pages for user {}", userId, e);
        }
        return pages;
    }

    private List<CollectionElement> getCollectionElements(Connection conn, long userId, int pageIdx) throws SQLException {
        List<CollectionElement> elements = new ArrayList<>();
        String sql = "SELECT element_index, element_id, element_type FROM user_collection_elements WHERE user_id = ? AND page_idx = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setInt(2, pageIdx);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    elements.add(CollectionElement.newBuilder()
                            .setIdx(rs.getInt("element_index"))
                            .setId(rs.getInt("element_id"))
                            .setType(rs.getInt("element_type"))
                            .build());
                }
            }
        }
        return elements;
    }

    public void saveCollectionPages(long userId, List<CollectionPage> pages) {
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Delete existing pages and elements
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM user_collection_pages WHERE user_id = ?")) {
                    ps.setLong(1, userId);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM user_collection_elements WHERE user_id = ?")) {
                    ps.setLong(1, userId);
                    ps.executeUpdate();
                }

                String sqlPage = "INSERT INTO user_collection_pages (user_id, idx, title, layout_id, theme_id, transition_id, page_sort) VALUES (?, ?, ?, ?, ?, ?, ?)";
                String sqlElem = "INSERT INTO user_collection_elements (user_id, page_idx, element_index, element_id, element_type) VALUES (?, ?, ?, ?, ?)";
                
                try (PreparedStatement psPage = conn.prepareStatement(sqlPage);
                     PreparedStatement psElem = conn.prepareStatement(sqlElem)) {
                    for (CollectionPage page : pages) {
                        psPage.setLong(1, userId);
                        psPage.setInt(2, page.getIdx());
                        psPage.setString(3, page.getTitle());
                        psPage.setInt(4, page.getLayoutId());
                        psPage.setInt(5, page.getThemeId());
                        psPage.setInt(6, page.getTransitionId());
                        psPage.setInt(7, page.getPageSort());
                        psPage.addBatch();

                        for (CollectionElement elem : page.getCollectionElementsList()) {
                            psElem.setLong(1, userId);
                            psElem.setInt(2, page.getIdx());
                            psElem.setInt(3, elem.getIdx());
                            psElem.setLong(4, elem.getId());
                            psElem.setInt(5, elem.getType());
                            psElem.addBatch();
                        }
                    }
                    psPage.executeBatch();
                    psElem.executeBatch();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error("Failed to save collection pages for user {}", userId, e);
        }
    }
}
