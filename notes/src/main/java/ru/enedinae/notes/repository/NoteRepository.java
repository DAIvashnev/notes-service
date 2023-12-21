package ru.enedinae.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.enedinae.notes.model.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByNameStartingWith(String name);

    @Modifying
    @Query(nativeQuery=true, value = "SELECT * FROM notes WHERE status NOT LIKE 'DELETED'")
    List<Note> findAll();


    @Modifying
    @Query(nativeQuery=true, value = "UPDATE notes SET status = 'DELETED', update_time = now() WHERE notes.id=:id")
    void deleteById(Long id);
}
