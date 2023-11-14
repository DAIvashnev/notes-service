package ru.enedinae.notes.repository;

import java.sql.ResultSet;

public interface NoteRepository {

    ResultSet selectAll();
    ResultSet selectById(int id);
}
