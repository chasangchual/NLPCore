package com.bloomingbread.core.nlp.tools.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkNotNull;

public class SQLiteJDBCAdapter {
    Properties properties = null ;
    private final String DBURL_PREFIX = "jdbc:sqlite:";
    Connection conn = null;

    public SQLiteJDBCAdapter() {
        properties = new Properties();
    }

    public SQLiteJDBCAdapter(final Properties properties) {
        checkNotNull(properties);
        this.properties = properties;
    }

    public synchronized Connection getConnection(final String connUrl) throws SQLException, ClassNotFoundException {
        return getConnection(connUrl, false);
    }

    public synchronized Connection getConnection(final String connUrl, final boolean reconnect) throws SQLException, ClassNotFoundException {
        if(reconnect || conn == null || conn.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(DBURL_PREFIX + connUrl);
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                throw e;
            }
            System.out.println("Opened database successfully");
        }
        return conn;
    }
}