package ru.enedinae.notes.db;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Properties;

public class DataBaseManager {
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private final Connection connection;
    private final Statement statement;
    private final Properties properties;

    public DataBaseManager() {
        properties = new Properties();
        try {
            InputStream is = DataBaseManager.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(is);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            statement = connection.createStatement();
            is = DataBaseManager.class.getClassLoader().getResourceAsStream("schema.sql");
            String schemaSql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(schemaSql);
            is.close();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw   new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
