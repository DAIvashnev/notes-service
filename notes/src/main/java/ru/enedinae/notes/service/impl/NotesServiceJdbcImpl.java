package ru.enedinae.notes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.enedinae.notes.exceptions.NotCorrectFormatDataException;
import ru.enedinae.notes.exceptions.NotHaveNotesException;
import ru.enedinae.notes.exceptions.NoteNotFoundException;
import ru.enedinae.notes.entity.Note;
import ru.enedinae.notes.repository.NoteRepository;
import ru.enedinae.notes.service.NotesService;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Optional;

@Service
public class NotesServiceJdbcImpl implements NotesService {
    private final NoteRepository repository;
    @Autowired
    public NotesServiceJdbcImpl(NoteRepository repository) {
        this.repository = repository;
    }

    public Note createNote(String name, String desc, String deadLine) throws NotCorrectFormatDataException {
        if(!validationDateTime(deadLine)) {
            throw new NotCorrectFormatDataException("Не корректный формат даты.");
        }
        Note note = new Note(name, desc, deadLine);
        repository.save(note);
        return note;
    }

    public List<Note> getAllNotes() throws NotHaveNotesException {
        if(repository.findAll().isEmpty()) {
            throw new NotHaveNotesException("У вас нет заметок.");
        }
        return repository.findAll();
    }

    public Note getNoteById(Long id) throws NoteNotFoundException {
        Optional<Note> note = repository.findById(id);
        if(note.isEmpty()) {
            throw new NoteNotFoundException("Заметки с таким ID не существует.");
        }
        return note.get();
    }

    public List<Note> getNoteByName(String name) throws NoteNotFoundException {
        List<Note> note = repository.findByNameStartingWith(name);
        if(note.isEmpty()) {
            throw new NoteNotFoundException("Заметки с таким именем не существует.");
        }
        return note;
    }

    public void deleteNoteById(Long id) throws NoteNotFoundException {
        Optional<Note> note = repository.findById(id);
        if(note.isEmpty()) {
            throw new NoteNotFoundException("Заметки с таким ID не существует.");
        }
        repository.deleteById(id);
    }

    public void updateNote(Long id, Note note) throws NoteNotFoundException, NotCorrectFormatDataException {
        Optional<Note> checkNote = repository.findById(id);
        if(checkNote.isEmpty()) {
            throw new NoteNotFoundException("Заметки с таким ID не существует.");
        }
        Note newNote = checkNote.get();
        Optional<String> st;
        if((st = Optional.ofNullable(note.getName())).isPresent()) {
            newNote.setName(st.get());
        }
        if((st = Optional.ofNullable(note.getStatus())).isPresent()) {
            newNote.setStatus(st.get());
        }
        if((st = Optional.ofNullable(note.getDescription())).isPresent()) {
            newNote.setDescription(st.get());
        }
        if((st = Optional.ofNullable(note.getDeadline())).isPresent()) {
            newNote.setDeadline(st.get());
        }
        newNote.setUpdate_time(LocalDateTime.now());
        if(!validationDateTime(newNote.getDeadline())) {
            throw new NotCorrectFormatDataException("Не корректный формат даты.");
        }
        repository.save(newNote);
    }

    public void checkDeadline() {
        repository.checkDeadline();
    }

    private boolean validationDateTime(String deadline) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withResolverStyle(ResolverStyle.SMART);
        if (deadline.isEmpty()) {
            return true;
        }
        try {
            LocalDateTime dateTime = LocalDateTime.parse(deadline, formatter);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
