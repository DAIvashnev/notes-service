package ru.enedinae.notes.repositories.impl;

import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.repositories.NotesRepository;

import java.util.Optional;

public class NotesRepositoryJdbcImpl implements NotesRepository {

    @Override
    public boolean createNewNote() {
        return true;
    }

    public Optional<Note> findById(Long id) {
        return Optional.empty();
    }
}
