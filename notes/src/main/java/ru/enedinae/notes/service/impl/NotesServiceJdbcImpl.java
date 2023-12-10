package ru.enedinae.notes.service.impl;

import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.repository.Repository;
import ru.enedinae.notes.service.NotesServiceJdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class NotesServiceJdbcImpl implements NotesServiceJdbc {
    private Repository noteRepository;

    /*public NotesServiceJdbcImpl(Repository noteRepository) {
        this.noteRepository = noteRepository;
    }*/

    public boolean insertNote() {
        return false;
    }

    public Optional<Note> selectById(Integer id) {
        return Optional.empty();
    }

    public Optional<Note> selectByName(String name) {
        return Optional.empty();
    }

    public boolean deleteById(Integer id) {
        return false;
    }

    public boolean deleteByName(String name) {
        return false;
    }

    public boolean updateNote(String date) {
        return false;
    }

    public void selectAllNotes(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println(rs.getString("id") + " "
                        + rs.getString("name") + " "
                        + rs.getString("description") + " "
                        + rs.getString("deadline") + " "
                        + rs.getTimestamp("create_time") + " "
                        + rs.getTimestamp("update_time"));
            }
        }catch (SQLException e) {
            System.out.println(e);
        }
    }
}
