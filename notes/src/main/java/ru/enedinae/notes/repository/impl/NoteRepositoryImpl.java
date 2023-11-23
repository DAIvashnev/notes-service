package ru.enedinae.notes.repository.impl;

import ru.enedinae.notes.db.DataBaseManager;
import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.repository.NoteRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NoteRepositoryImpl implements NoteRepository {
    private final DataBaseManager dataBaseManager;
    private static PreparedStatement preparedStatement;
    private static final String INSERT = "INSERT INTO notes (name, description, deadline, status, create_time, update_time) " +
            "VALUES(?, ?, ?, ?, now(), now())";
    private static final String SELECT_ALL = "SELECT * FROM notes";
    private static final String SELECT_BY_ID = "SELECT * FROM notes WHERE id = ?";
    private static final String SELECT_BY_NAME = "SELECT * FROM notes WHERE name = ?";
    private static final String DELETE_BY_ID = "DELETE FROM notes WHERE id = ?";
    private static final String UPDATE_NOTES = "UPDATE notes SET name = ?, status = ?, description = ?, deadline = ?, update_time = now() WHERE id = ?";

    public NoteRepositoryImpl(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public void insertNote(Note note) {
        try {
            preparedStatement = dataBaseManager.getConnection()
                    .prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, note.getName());
            preparedStatement.setString(2, note.getDescription());
            preparedStatement.setString(3, note.getDeadline());
            preparedStatement.setString(4, note.getStatus().toString());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while(rs.next()) {
                note.setId(rs.getInt(1));
            }
            preparedStatement.setInt(1, note.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ResultSet selectAll() {
        try {
            return dataBaseManager.getConnection().prepareStatement(SELECT_ALL).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ResultSet selectById(Integer id) {
        try {
            preparedStatement = dataBaseManager.getConnection().prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ResultSet selectByName(String name) {
        try {
            preparedStatement = dataBaseManager.getConnection().prepareStatement(SELECT_BY_NAME);
            preparedStatement.setString(1, name);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int deleteById(Integer id) {
        try {
            preparedStatement = dataBaseManager.getConnection().prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean updateNote(Note updateNote) {
        try {
            preparedStatement = dataBaseManager.getConnection().prepareStatement(UPDATE_NOTES);
            preparedStatement.setInt(5, updateNote.getId());
            preparedStatement.setString(1, updateNote.getName());
            preparedStatement.setString(2, updateNote.getStatus().toString());
            preparedStatement.setString(3, updateNote.getDescription());
            preparedStatement.setString(4, updateNote.getDeadline());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
