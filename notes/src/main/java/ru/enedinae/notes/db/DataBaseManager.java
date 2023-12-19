package ru.enedinae.notes.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.enedinae.notes.ui.impl.CommandLineUiImpl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

@Component
public class DataBaseManager implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.user}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;

    @Autowired
    public DataBaseManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate jdbcTemplate() {
        return jdbcTemplate;
    }

    public void executeInitScript() {
        try (
                Connection connection = DriverManager.getConnection(url,user,password);
                InputStream schemaInputStream =  DataBaseManager.class.getClassLoader().getResourceAsStream("schema.sql")) {
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