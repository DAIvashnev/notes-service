package ru.enedinae.notes.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Properties;

@Component
public class DataBaseManager {
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private final Properties properties;
    private String schemaSql;

    @Autowired
    public DataBaseManager() {
        properties = new Properties();
        try (InputStream propertyInputStream = DataBaseManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(propertyInputStream);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");
            executeInitScript();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeInitScript() {
        try (
                Connection connection = getConnection();
                InputStream schemaInputStream =  DataBaseManager.class.getClassLoader().getResourceAsStream("schema.sql")) {
            Statement statement = connection.createStatement();
            schemaSql = new String(schemaInputStream.readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(schemaSql);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
