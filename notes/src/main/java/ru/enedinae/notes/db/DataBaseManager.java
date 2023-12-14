package ru.enedinae.notes.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

@Component
public class DataBaseManager {
    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;

    public JdbcTemplate jdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return new JdbcTemplate(dataSource);
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