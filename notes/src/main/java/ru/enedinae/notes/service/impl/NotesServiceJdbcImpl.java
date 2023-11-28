package ru.enedinae.notes.service.impl;

import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.repository.NoteRepository;
import ru.enedinae.notes.service.NotesService;

import java.util.List;
import java.util.Optional;

public class NotesServiceJdbcImpl implements NotesService {
    private final NoteRepository repository;

    public NotesServiceJdbcImpl(NoteRepository repository) {
        this.repository = repository;
    }

    public Note createNote(String name, String desc, String deadLine) {
        Note note = new Note(name, desc, deadLine);
        repository.insertNote(note);
        return note;
    }

    public List<Note> getAllNotes() {
        return repository.selectAll();
    }

    public Optional<Note> getNoteById(Integer id) {
        return Optional.ofNullable(repository.selectById(id));
    }

    public List<Note> getNoteByName(String name) {
        return repository.selectByName(name);
    }

    public boolean deleteNoteById(Integer id) {
        return repository.deleteById(id) != 0;
    }

    public boolean updateNote(Note updateNote) {
        return repository.updateNote(updateNote);
    }
}
