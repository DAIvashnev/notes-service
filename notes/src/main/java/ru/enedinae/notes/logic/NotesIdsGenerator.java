package ru.enedinae.notes.logic;

public class NotesIdsGenerator {
    private int id = 0;
    private static NotesIdsGenerator INSTANCE;

    private NotesIdsGenerator() {
    }

    public static NotesIdsGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotesIdsGenerator();
        }
        return INSTANCE;
    }

    public int generateId() {
        return id++;
    }
}
