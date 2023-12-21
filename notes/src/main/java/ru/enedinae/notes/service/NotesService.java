package ru.enedinae.notes.service;

import ru.enedinae.notes.model.Note;

import java.util.List;
import java.util.Optional;

public interface NotesService {
    Note createNote(String name, String desc, String deadLine);
    List<Note> getAllNotes();
    Optional<Note> getNoteById(Long id);
    List<Note> getNoteByName(String name);
    void deleteNoteById(Long id);
    void updateNote(Note updateNote);
}
