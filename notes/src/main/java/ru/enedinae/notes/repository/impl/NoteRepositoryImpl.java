package ru.enedinae.notes.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.enedinae.notes.db.DataBaseManager;
import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.repository.NoteRepository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
public class NoteRepositoryImpl implements NoteRepository {
    private final DataBaseManager dataBaseManager;
    private final BeanPropertyRowMapper<Note> noteMapper;
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
    public NoteRepositoryImpl(DataBaseManager dataBaseManager, BeanPropertyRowMapper<Note> noteMapper) {
        this.dataBaseManager = dataBaseManager;
        this.noteMapper = noteMapper;
    }

    public void insertNote(Note note) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        dataBaseManager.jdbcTemplate().update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, note.getName());
            ps.setString(2, note.getDescription());
            ps.setString(3, note.getDeadline());
            ps.setString(4, note.getStatus().toString());
            return ps;
        }, keyHolder);
        note.setId(Math.toIntExact((Long)keyHolder.getKeys().get("id")));
    }

    public List<Note> selectAll() {
        return dataBaseManager.jdbcTemplate().query(SELECT_ALL, noteMapper);
    }

    public Note selectById(Integer id) {
        return dataBaseManager.jdbcTemplate().query(SELECT_BY_ID, noteMapper, id)
                .stream().findAny().orElse(null);
    }

    public List<Note> selectByName(String name) {
        return dataBaseManager.jdbcTemplate().query(SELECT_BY_NAME, noteMapper,name+"%");
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
