package com.jad.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
    private static DBConnector instance;
    private Connection connection;

    private DBConnector() {
    }

    public static DBConnector getInstance() {
        if (DBConnector.instance == null) {
            DBConnector.instance = new DBConnector();
        }
        return DBConnector.instance;
    }

    public Statement getStatement() {
        try {
            return this.getConnection().createStatement();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            this.connect();
        }
        return this.connection;
    }

    public void connect() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mariadb://jeanaymeric.hd.free.fr:8601/nfsproject",
                "junia_isen2_2526",
                "yagoulou");
    }

    public void disconnect() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
            System.out.println("Disconnected from the database.");
        }
    }
}