package ru.enedinae.notes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.repository.NoteRepository;
import ru.enedinae.notes.service.NotesService;
import java.util.List;
import java.util.Optional;

@Component
public class NotesServiceJdbcImpl implements NotesService {

    private final NoteRepository repository;
    @Autowired
    public NotesServiceJdbcImpl(NoteRepository repository) {
        this.repository = repository;
    }


    public Note createNote(String name, String desc, String deadLine) {
        Note note = new Note(name, desc, deadLine);
        repository.save(note);
        return note;
    }

    public List<Note> getAllNotes() {
        return repository.findAll();
    }

    @Override
    public boolean updateNote(Note updateNote) {
        return false;
    }

    public Optional<Note> getNoteById(Long id) {
        return repository.findById(id);
    }

    public List<Note> getNoteByName(String name) {
        return repository.findByNameStartingWith(name);
    }

    public void deleteNoteById(Long id) {
        repository.deleteById(id);
    }

    /*public boolean updateNote(Note updateNote) { return repository.updateNote(updateNote) > 0; }*/
}
