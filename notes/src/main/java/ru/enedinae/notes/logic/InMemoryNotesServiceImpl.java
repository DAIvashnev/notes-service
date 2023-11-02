package ru.enedinae.notes.logic;

import ru.enedinae.notes.NotesService;
import ru.enedinae.notes.models.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryNotesServiceImpl implements NotesService {
    private final List<Note> notes = new ArrayList<>();

    public Note createNote(String name, String desc, String deadLine) {
        Note note = new Note(name,desc,deadLine);
        notes.add(note);
        return note;
    }

    public List<Note> getAllNotes() {
        return notes;
    }

    public Optional<Note> getNoteById(Integer id) {
        for(Note note : notes) {
            Optional<Integer> op = Optional.ofNullable(note.getId());
            if(op.isPresent() && note.getId().equals(id)) {
                return Optional.of(note);
            }
        }
        return Optional.empty();
    }

    public Optional<Note> getNoteByName(String name) {
        for(Note note : notes) {
            Optional<String> op = Optional.ofNullable(note.getName());
            if(op.isPresent() && note.getName().equals(name)) {
                return Optional.of(note);
            }
        }
        return Optional.empty();
    }

    public boolean deleteNoteById(Integer id) {
        return notes.removeIf(note -> note.getId().equals(id));
    }

    public boolean deleteNoteByName(String name) {
        return notes.removeIf(note -> note.getName().equals(name));
    }

    public boolean updateNote(Note updateNote) {
        for (Note note : notes) {
            if(note.getId().equals(updateNote.getId())) {
                note = updateNote;
                return true;
            }
        }
        return false;
    }
}
