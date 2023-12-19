package ru.enedinae.notes.springConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import ru.enedinae.notes.model.Note;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;

    @Bean
    public DataSource DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
    @Bean
    public JdbcTemplate JdbcTemplate() {
        return new JdbcTemplate(DataSource());
    }

    @Bean
    public BeanPropertyRowMapper<Note> BeanPropertyRowMapper() {
        return new BeanPropertyRowMapper<>(Note.class);
    }

}
