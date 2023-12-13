package ru.enedinae.notes.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.enedinae.notes.db.DataBaseManager;

import ru.enedinae.notes.mapper.NoteMapper;
import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.repository.NoteRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class NoteRepositoryImpl implements NoteRepository{
    private final DataBaseManager dataBaseManager;
    private final NoteMapper noteMapper;
    private static final String INSERT = "INSERT INTO notes (name, description, deadline, status, create_time, update_time) " +
            "VALUES(?, ?, ?, ?, now(), now())";
    private static final String SELECT_ALL = "SELECT * FROM notes WHERE status NOT LIKE 'DELETED'";
    private static final String SELECT_BY_ID = "SELECT * FROM notes WHERE id = ?";
    private static final String SELECT_BY_NAME = "SELECT * FROM notes WHERE name LIKE ?";
    private static final String DELETE_BY_ID = "UPDATE notes SET status = 'DELETED', update_time = now() WHERE id = ?";
    private static final String UPDATE_NOTES = "UPDATE notes SET name = ?, status = ?, description = ?, deadline = ?, update_time = now() WHERE id = ?";
    private static final String CHECK_DEADLINE =
            "UPDATE notes SET status = 'EXPIRED', update_time = now() WHERE status != 'EXPIRED' and deadline != '' and TIMESTAMPTZ(deadline) < CURRENT_TIMESTAMP;";
    @Autowired
    public NoteRepositoryImpl(DataBaseManager dataBaseManager, NoteMapper noteMapper) {
        this.dataBaseManager = dataBaseManager;
        this.noteMapper = noteMapper;
        this.dataBaseManager.executeInitScript();
    }

    public void insertNote(Note note) {
        //KeyHolder keyHolder = new GeneratedKeyHolder();
        dataBaseManager.jdbcTemplate().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, note.getName());
            ps.setString(2, note.getDescription());
            ps.setString(3, note.getDeadline());
            ps.setString(4, note.getStatus().toString());
            ps.executeUpdate();
            ps.getGeneratedKeys();
            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()) {
                note.setId(rs.getInt(1));
            }
            ps.setInt(1, note.getId());
            return ps;
        }/*, keyHolder*/);
        //System.out.println(keyHolder.getKey());
        //note.setId(Integer.parseInt(String.valueOf(keyHolder.getKey())));
        //dataBaseManager.jdbcTemplate().update("UPDATE notes SET id = ? WHERE name = ?", keyHolder.getKey(), note.getName());
    }

    public List<Note> selectAll() {
        return dataBaseManager.jdbcTemplate().query(SELECT_ALL, new NoteMapper());
    }

    public Note selectById(Integer id) {
        return dataBaseManager.jdbcTemplate().query(SELECT_BY_ID, new Object[]{id}, new NoteMapper())
                .stream().findAny().orElse(null);
    }

    public List<Note> selectByName(String name) {
        return dataBaseManager.jdbcTemplate().query(SELECT_BY_NAME, new String[]{name + "%"}, new NoteMapper());
    }

    public int deleteById(Integer id) {
        return dataBaseManager.jdbcTemplate().update(DELETE_BY_ID, id);
    }

    public int updateNote(Note updateNote) {
        return dataBaseManager.jdbcTemplate().update(UPDATE_NOTES, updateNote.getName(), updateNote.getStatus().toString(), updateNote.getDescription(),
                updateNote.getDeadline(), updateNote.getId());
    }

    public void checkDeadline() {
        dataBaseManager.jdbcTemplate().update(CHECK_DEADLINE);
    }
}


/*
public void insertNote(Note note) {
        try(PreparedStatement preparedStatement = dataBaseManager.getConnection()
                .prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
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

    public List<Note> selectAll() {
        try(PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(SELECT_ALL)){
            return noteMapper.map(preparedStatement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Note selectById(Integer id) {
        try(PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            return noteMapper.mapNote(preparedStatement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Note> selectByName(String name) {
        try(PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(SELECT_BY_NAME)) {
            preparedStatement.setString(1, name+"%");
            return noteMapper.map(preparedStatement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int deleteById(Integer id) {
        try(PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean updateNote(Note updateNote) {
        try(PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(UPDATE_NOTES)) {
            preparedStatement.setString(1, updateNote.getName());
            preparedStatement.setString(2, updateNote.getStatus().toString());
            preparedStatement.setString(3, updateNote.getDescription());
            preparedStatement.setString(4, updateNote.getDeadline());
            preparedStatement.setInt(5, updateNote.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void checkDeadline() {
        try(PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(CHECK_DEADLINE)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 */
