package ru.enedinae.notes.service;

import ru.enedinae.notes.model.Note;

import java.util.List;
import java.util.Optional;

public interface NotesService {
    Note createNote(String name, String desc, String deadLine);
    List<Note> getAllNotes();
    Optional<Note> getNoteById(Integer id);
    Optional<List<Note>> getNoteByName(String name);
    boolean deleteNoteById(Integer id);
    boolean updateNote(Note updateNote);
}
