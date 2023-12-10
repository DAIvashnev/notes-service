package ru.enedinae.notes.service;

import ru.enedinae.notes.model.Note;

import java.sql.ResultSet;
import java.util.Optional;

public interface NotesServiceJdbc {
    boolean insertNote();
    Optional<Note> selectById(Integer id);
    Optional<Note> selectByName(String name);
    boolean deleteById(Integer id);
    boolean deleteByName(String name);
    boolean updateNote(String date);
    void selectAllNotes(ResultSet rs);
}
