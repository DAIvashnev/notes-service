package ru.enedinae.notes.app;

import ru.enedinae.notes.service.impl.InMemoryNotesServiceImpl;
import ru.enedinae.notes.service.impl.NotesServiceJdbcImpl;
import ru.enedinae.notes.ui.impl.CommandLineJdbcImpl;
import ru.enedinae.notes.ui.impl.CommandLineUiImpl;

import java.util.Scanner;

public class Application {
    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Start:\n1 - Service mode?\n2 - DataBase mode?\n");
        String mode = scanner.nextLine();
        if(mode.equals("1")) {
            new CommandLineUiImpl(new InMemoryNotesServiceImpl()).start();
        } else if(mode.equals("2")) {
            new CommandLineJdbcImpl(new NotesServiceJdbcImpl()).start();
        }
        scanner.close();
    }
}