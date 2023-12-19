package ru.enedinae.notes.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

@Component
public class CreateTable implements CommandLineRunner {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;

    public void executeInitScript() {
        try (
                Connection connection = DriverManager.getConnection(url,user,password);
                InputStream schemaInputStream =  CreateTable.class.getClassLoader().getResourceAsStream("schema.sql")) {
            Statement statement = connection.createStatement();
            String schemaSql = new String(schemaInputStream.readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(schemaSql);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        executeInitScript();
    }
}