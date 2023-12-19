package ru.enedinae.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ru.enedinae.notes.model.Note;

import java.nio.file.LinkOption;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
