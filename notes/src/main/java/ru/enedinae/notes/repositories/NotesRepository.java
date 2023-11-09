package ru.enedinae.notes.repositories;

import ru.enedinae.notes.model.Note;
import java.util.Optional;

public interface NotesRepository{
    boolean createNewNote();
    Optional<Note> findById(Long id);
}
