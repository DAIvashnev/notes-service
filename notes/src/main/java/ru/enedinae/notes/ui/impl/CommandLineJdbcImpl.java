package ru.enedinae.notes.ui.impl;

import ru.enedinae.notes.repository.DataBaseManager;
import ru.enedinae.notes.service.NotesServiceJdbc;
import ru.enedinae.notes.ui.UserInterface;

import java.sql.*;
import java.util.Scanner;

public class CommandLineJdbcImpl implements UserInterface {
    private final NotesServiceJdbc notesServiceJdbc;
    private final Statement statement;
    private ResultSet rs;
    private static final DataBaseManager dataBaseManager = new DataBaseManager();
    private final Scanner scanner = new Scanner(System.in);

    public CommandLineJdbcImpl(NotesServiceJdbc notesServiceJdbc) {
        this.notesServiceJdbc = notesServiceJdbc;
        try {
            statement = dataBaseManager.getConnection().createStatement();
        } catch (SQLException e) {
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
        dataBaseManager.closeConnection();
        System.exit(0);
    }
}