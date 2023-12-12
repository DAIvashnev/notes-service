package ru.enedinae.notes.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

@Component
public class DataBaseManager {
    @Value("${db.url}")
    private String URL;
    @Value("${db.user}")
    private String USER;
    @Value("${db.password}")
    private String PASSWORD;
    private String schemaSql;

    public Connection getConnection() {
        executeInitScript();
        try {
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeInitScript() {
        try (
                Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
                InputStream schemaInputStream =  DataBaseManager.class.getClassLoader().getResourceAsStream("schema.sql")) {
            Statement statement = connection.createStatement();
            schemaSql = new String(schemaInputStream.readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(schemaSql);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
