package ru.enedinae.notes.repository.impl;

import ru.enedinae.notes.db.DataBaseManager;
import ru.enedinae.notes.repository.NoteRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteRepositoryImpl implements NoteRepository {

    private final DataBaseManager dataBaseManager;

    public NoteRepositoryImpl(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    @Override
    public ResultSet selectAll() {
        try {
            return dataBaseManager.getConnection().prepareStatement("SELECT * FROM NOTE").executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet selectById(int id) {
        try {
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement("SELECT * FROM NOTE WHERE id = ?");
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
