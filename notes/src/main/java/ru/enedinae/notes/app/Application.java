package ru.enedinae.notes.app;

import ru.enedinae.notes.db.DataBaseManager;
import ru.enedinae.notes.mapper.NoteMapper;
import ru.enedinae.notes.repository.impl.NoteRepositoryImpl;
import ru.enedinae.notes.service.NotesService;
import ru.enedinae.notes.service.impl.InMemoryNotesServiceImpl;
import ru.enedinae.notes.service.impl.NotesServiceJdbcImpl;
import ru.enedinae.notes.ui.impl.CommandLineUiImpl;

import java.util.Objects;
import java.util.Scanner;

public class Application {
    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Start:\n1 - Service mode?\n2 - DataBase mode?\n");
        String mode = scanner.nextLine();
        new CommandLineUiImpl(resolveService(mode)).start();
        scanner.close();
    }

    private static NotesService resolveService(String mode) {
        if (Objects.equals("1", mode)) {
            return new InMemoryNotesServiceImpl();
        } else if (Objects.equals("2", mode)){
            return new NotesServiceJdbcImpl(
                    new NoteRepositoryImpl(new DataBaseManager()),
                    new NoteMapper()
            );
        }
        throw new IllegalArgumentException("Нет такого выбора");
    }
}