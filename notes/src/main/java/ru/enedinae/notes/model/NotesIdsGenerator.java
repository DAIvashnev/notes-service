package ru.enedinae.notes.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotesIdsGenerator {
    private int id = 0;
    private static NotesIdsGenerator INSTANCE;

    @Autowired
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
