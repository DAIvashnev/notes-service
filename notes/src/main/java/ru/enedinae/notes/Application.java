package ru.enedinae.notes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableJpaRepositories("ru.enedinae.notes.repository")
@EntityScan("ru.enedinae.notes.model")
@SpringBootApplication
public class Application {
    public static void main(String[] args)  {
        SpringApplication.run(Application.class);
    }
}