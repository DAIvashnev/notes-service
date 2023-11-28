package ru.enedinae.notes.repository;

import ru.enedinae.notes.model.Note;

import java.sql.ResultSet;
import java.util.List;

public interface NoteRepository {
    void insertNote(Note note);
    List<Note> selectAll();
    Note selectById(Integer id);
    List<Note> selectByName(String name);
    int deleteById(Integer id);
    boolean updateNote(Note updateNote);

}
