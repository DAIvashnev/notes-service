package ru.enedinae.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.enedinae.notes.entity.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByNameStartingWith(String name);

    @Modifying
    @Query(nativeQuery=true, value = "SELECT * FROM notes WHERE status NOT LIKE 'DELETED' ORDER BY status DESC")
    List<Note> findAll();

    @Modifying
    @Query(nativeQuery=true, value = "UPDATE notes n SET status = 'DELETED', time_update = now() WHERE n.id=:id")
    void deleteById(Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery=true, value = "UPDATE notes SET status = 'EXPIRED', time_update = now() WHERE status != 'EXPIRED' " +
            "and status != 'DELETED' and deadline != '' and TIMESTAMPTZ(deadline) < CURRENT_TIMESTAMP;")
    void checkDeadline();
}
