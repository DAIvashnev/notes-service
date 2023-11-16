package ru.enedinae.notes.repository;

import ru.enedinae.notes.model.Note;

import java.sql.ResultSet;

public interface NoteRepository {
    void insertNote(Note note);
    ResultSet selectAll();
    ResultSet selectById(Integer id);
    int deleteById(Integer id);
    int deleteById(String name);
}