package ru.enedinae.notes.service.impl;

import ru.enedinae.notes.mapper.NoteMapper;
import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.repository.NoteRepository;
import ru.enedinae.notes.service.NotesService;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class NotesServiceJdbcImpl implements NotesService {

    private final NoteRepository repository;
    private final NoteMapper noteMapper;

    public NotesServiceJdbcImpl(NoteRepository repository, NoteMapper noteMapper) {
        this.repository = repository;
        this.noteMapper = noteMapper;
    }

    @Override
    public Note createNote(String name, String desc, String deadLine) {
        return null;
    }

    @Override
    public List<Note> getAllNotes() {
        ResultSet resultSet = repository.selectAll();
        return noteMapper.map(resultSet);
    }

    @Override
    public Optional<Note> getNoteById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Note> getNoteByName(String name) {
        return Optional.empty();
    }

    @Override
    public boolean deleteNoteById(Integer id) {
        return false;
    }

    @Override
    public boolean deleteNoteByName(String name) {
        return false;
    }

    @Override
    public boolean updateNote(Note updateNote) {
        return false;
    }
}
