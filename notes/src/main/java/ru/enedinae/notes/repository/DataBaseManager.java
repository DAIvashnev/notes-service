package ru.enedinae.notes.repository;

import java.io.FileInputStream;
import java.io.IOException;
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
    private final FileInputStream fis;
    private final Properties properties;

    public DataBaseManager() {
        properties = new Properties();
        try {
            fis = new FileInputStream("notes/src/main/resources/db.properties");
            properties.load(fis);
            fis.close();
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            statement = connection.createStatement();
            String schemaSql = Files.readString(Path.of("notes/src/main/resources/schema.sql"));
            statement.execute(schemaSql);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
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
