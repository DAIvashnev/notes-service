package ru.enedinae.notes.app;

import ru.enedinae.notes.db.DataBaseManager;
import ru.enedinae.notes.mapper.NoteMapper;
import ru.enedinae.notes.repository.impl.NoteRepositoryImpl;
import ru.enedinae.notes.service.NotesService;
import ru.enedinae.notes.service.impl.InMemoryNotesServiceImpl;
import ru.enedinae.notes.service.impl.NotesServiceJdbcImpl;
import ru.enedinae.notes.ui.impl.CommandLineUiImpl;

import java.util.Objects;

public class Application {
    public static void main(String[] args)  {
        new CommandLineUiImpl(resolveService(args[0])).start();
    }

    private static NotesService resolveService(String args) {
        if (Objects.equals(args, "--service.mode=in-memory")) {
            return new InMemoryNotesServiceImpl();
        } else if (Objects.equals(args, "--service.mode=data-base")){
            return new NotesServiceJdbcImpl(new NoteRepositoryImpl(new DataBaseManager(), new NoteMapper()));
        }
        throw new IllegalArgumentException("Нет такого выбора");
    }
}