package ru.enedinae.notes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.enedinae.notes.repository.CreateTable;
import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.repository.NoteRepository;
import ru.enedinae.notes.service.NotesService;
import java.util.List;
import java.util.Optional;

@Component
public class NotesServiceJdbcImpl implements NotesService {
    private  NoteRepository repository;
    private final CreateTable createTable;
    @Autowired
    public NotesServiceJdbcImpl(CreateTable createTable) {
        this.createTable = createTable;
    }

    /*public Note createNote(String name, String desc, String deadLine) {
        Note note = new Note(name, desc, deadLine);
        repository.insertNote(note);
        return note;
    }*/

    @Override
    public Note createNote(String name, String desc, String deadLine) {
        return null;
    }

    public List<Note> getAllNotes() {
        return repository.findAll();
    }

    @Override
    public Optional<Note> getNoteById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Note> getNoteByName(String name) {
        return null;
    }

    @Override
    public boolean deleteNoteById(Integer id) {
        return false;
    }

    @Override
    public boolean updateNote(Note updateNote) {
        return false;
    }

    /*public Optional<Note> getNoteById(Integer id) {
        return Optional.ofNullable(repository.selectById(id));
    }

    public List<Note> getNoteByName(String name) { return repository.selectByName(name); }

    public boolean deleteNoteById(Integer id) {
        return repository.deleteById(id) != 0;
    }

    public boolean updateNote(Note updateNote) { return repository.updateNote(updateNote) > 0; }*/
}
