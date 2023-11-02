package ru.enedinae.notes.app;

import ru.enedinae.notes.logic.CommandLineUiImpl;

public class Application {
    //private UserInterface ui;
    public static void main(String[] args)  {
        new CommandLineUiImpl().start();
    }
}


