package ru.enedinae.notes.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

@Component
public class DataBaseManager {
    private final JdbcTemplate jdbcTemplate;
    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
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
}