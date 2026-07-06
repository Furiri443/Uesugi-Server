package com.emu.tqqserver.db.dao;

import com.emu.tqqserver.db.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDao {
    
    /**
     * Gets a connection to the database.
     * Use this in try-with-resources blocks.
     */
    protected Connection getConnection() throws SQLException {
        return DatabaseManager.getInstance().getConnection();
    }
    
    // Future expansion: we can add generic methods here 
    // for executing simple updates or queries to reduce boilerplate.
}
