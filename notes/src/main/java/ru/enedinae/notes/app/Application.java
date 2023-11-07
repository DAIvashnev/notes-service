package ru.enedinae.notes.app;

import ru.enedinae.notes.service.impl.InMemoryNotesServiceImpl;
import ru.enedinae.notes.ui.UserInterface;
import ru.enedinae.notes.ui.impl.CommandLineUiImpl;

public class Application {
    private UserInterface ui;
    public static void main(String[] args)  {

        new CommandLineUiImpl(new InMemoryNotesServiceImpl()).start();
    }
}