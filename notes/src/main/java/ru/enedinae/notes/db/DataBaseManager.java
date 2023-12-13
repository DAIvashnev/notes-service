package ru.enedinae.notes.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import java.io.*;
import java.sql.*;

@Component
public class DataBaseManager implements ResourceLoaderAware {
    private ResourceLoader resourceLoader;
    //private JdbcTemplate jdbcTemplate;
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
        Resource resource = resourceLoader.getResource("classpath:schema.sql");
        try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile().getPath()));
                Connection connection = DriverManager.getConnection(url,user,password);) {
            Statement statement = connection.createStatement();
            StringBuilder schemaSql = new StringBuilder();
            while (br.ready()) {
                schemaSql.append(br.readLine());
            }
            statement.execute(schemaSql.toString());
            schemaSql.delete(0, schemaSql.length());
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}



/*
public Connection getConnection() {
        try {
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeInitScript() {
        Resource resource = resourceLoader.getResource("classpath:schema.sql");
        try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile().getPath()));
                Connection connection = DriverManager.getConnection(url,user,password);) {
            Statement statement = connection.createStatement();
            StringBuilder schemaSql = new StringBuilder();
            while (br.ready()) {
                schemaSql.append(br.readLine());
            }
            statement.execute(schemaSql.toString());
            schemaSql.delete(0, schemaSql.length());
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
 */