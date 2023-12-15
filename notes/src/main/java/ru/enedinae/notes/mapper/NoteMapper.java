package ru.enedinae.notes.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.enedinae.notes.enumeration.NoteStatus;
import ru.enedinae.notes.model.Note;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class NoteMapper implements RowMapper<Note> {
    @Override
    public Note mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Note note = new Note();
        note.setId(resultSet.getInt("id"));
        note.setName(resultSet.getString("name"));
        note.setDescription(resultSet.getString("description"));
        note.setDeadline(resultSet.getString("deadline"));
        note.setStatus(NoteStatus.valueOf(resultSet.getString("status")));
        return note;
    }
}