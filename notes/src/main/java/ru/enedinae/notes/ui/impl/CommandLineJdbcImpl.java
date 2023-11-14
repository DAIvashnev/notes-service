package ru.enedinae.notes.ui.impl;

import ru.enedinae.notes.service.NotesServiceJdbc;
import ru.enedinae.notes.ui.UserInterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class CommandLineJdbcImpl implements UserInterface {
    private final NotesServiceJdbc notesServiceJdbc;
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private final Connection connection;
    private final Statement statement;
    private ResultSet rs;
    private FileInputStream fis;
    private static final Properties properties = new Properties();
    private final Scanner scanner = new Scanner(System.in);

    public CommandLineJdbcImpl(NotesServiceJdbc notesServiceJdbc) {
        this.notesServiceJdbc = notesServiceJdbc;
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

    public void start() {
        while (true) {
            System.out.println("\nСделайте выбор:\n\n"+"1 - Создать новую заметку.\n"+"2 - Удалить заметку.\n"+
                    "3 - Обновить заметку.\n"+"4 - Ваши заметки.\n"+"5 - Информация о заметке.\n"+"\n0 - Exit");
            try {
                switch (scanner.nextLine()) {
                    case "1":
                        notesServiceJdbc.insertNote();
                        break;
                    /*case "2":
                        menuDell();
                        break;
                    case "3":
                        updateNote();
                        break;*/
                    case "4":
                        rs = statement.executeQuery("SELECT * FROM notes");
                        System.out.println("Ваши заметки:");
                        notesServiceJdbc.selectAllNotes(rs);
                        break;
                    /*case "5":
                        infoByNote();
                        break;*/
                    case "0":
                        exit();
                        break;
                    default:
                        System.out.print("Нет такой команды. Введите номер команды показанный на экране.");
                        break;
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    private void exit() {
        scanner.close();
        System.exit(0);
    }
}
