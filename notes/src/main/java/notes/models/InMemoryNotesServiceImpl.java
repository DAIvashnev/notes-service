package notes.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static notes.models.NoteStatus.NEW;

public class InMemoryNotesServiceImpl implements NotesService {
    private final List<Note> notes = new ArrayList<>();

    public Note createNote(String name, String desc, String deadLine) {
        Note note = new Note(name,desc,deadLine,NEW);
        notes.add(note);
        return note;
    }

    public List<Note> getAllNotes() {
        return notes;
    }

    public Optional<Note> getNoteById(Integer id) {
        return Optional.empty();
    }

    public Optional<Note> getNoteByName(String name) {
        return Optional.empty();
    }

    public boolean deleteNoteById(Integer id) {
        return notes.removeIf(note -> note.getId().equals(id));
    }

    public boolean deleteNoteByName(String name) {
        return notes.removeIf(note -> note.getName().equals(name));
    }

    public boolean updateNote(Note updateNote) {
        return false;
    }
}
