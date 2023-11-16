package ru.enedinae.notes.service.impl;

import ru.enedinae.notes.mapper.NoteMapper;
import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.repository.NoteRepository;
import ru.enedinae.notes.service.NotesService;

import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class NotesServiceJdbcImpl implements NotesService {

    private final NoteRepository repository;
    private final NoteMapper noteMapper;

    public NotesServiceJdbcImpl(NoteRepository repository, NoteMapper noteMapper) {
        this.repository = repository;
        this.noteMapper = noteMapper;
    }

    public Note createNote(String name, String desc, String deadLine) {
        Note note = new Note(name, desc, deadLine);
        repository.insertNote(note);
        return note;
    }

    public List<Note> getAllNotes() {
        ResultSet resultSet = repository.selectAll();
        return noteMapper.map(resultSet);
    }

    public Optional<Note> getNoteById(Integer id) {
        ResultSet resultSet = repository.selectById(id);
        return Optional.of(noteMapper.mapNote(resultSet));
    }

    public Optional<Note> getNoteByName(String name) {
        ResultSet resultSet = repository.selectByName(name);
        return Optional.ofNullable(noteMapper.mapNote(resultSet));
    }

    public boolean deleteNoteById(Integer id) {
        return repository.deleteById(id) != 0;
    }

    public boolean deleteNoteByName(String name) {
        return repository.deleteById(name) != 0;
    }

    public boolean updateNote(Note updateNote) {
        return false;
    }
}
