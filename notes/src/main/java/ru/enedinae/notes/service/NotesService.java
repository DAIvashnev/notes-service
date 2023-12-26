package ru.enedinae.notes.service;

import ru.enedinae.notes.exceptions.NotCorrectFormatDataException;
import ru.enedinae.notes.exceptions.NotHaveNotesException;
import ru.enedinae.notes.exceptions.NoteNotFoundException;
import ru.enedinae.notes.entity.Note;

import java.util.List;

public interface NotesService {
    Note createNote(String name, String desc, String deadLine) throws NotCorrectFormatDataException;
    List<Note> getAllNotes() throws NotHaveNotesException;
    Note getNoteById(Long id) throws NoteNotFoundException;
    List<Note> getNoteByName(String name) throws NoteNotFoundException;
    void deleteNoteById(Long id) throws NoteNotFoundException;
    void updateNote(Long id, Note note) throws NoteNotFoundException, NotCorrectFormatDataException;
}
