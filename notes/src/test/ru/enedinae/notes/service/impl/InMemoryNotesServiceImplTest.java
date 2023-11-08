package ru.enedinae.notes.service.impl;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.testng.annotations.Test;
import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.service.NotesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.class)
public class InMemoryNotesServiceImplTest {
    private static final NotesService NOTES_SERVICE = new InMemoryNotesServiceImpl();
    private static final String NOTE_NAME = "NOTE_NAME";
    private static final String NOTE_DESC = "NOTE_DESC";
    private static final String NOTE_DEADLINE = "NOTE_DEADLINE";

    @Test
    @Order(1)
    public void createNoteTest() {
        NOTES_SERVICE.createNote(NOTE_NAME,NOTE_DESC,NOTE_DEADLINE);
        NOTES_SERVICE.createNote(NOTE_NAME,NOTE_DESC,NOTE_DEADLINE);
        NOTES_SERVICE.createNote(NOTE_NAME,NOTE_DESC,NOTE_DEADLINE);
        NOTES_SERVICE.createNote(NOTE_NAME,NOTE_DESC,NOTE_DEADLINE);
        assertFalse(NOTES_SERVICE.getAllNotes().isEmpty());
        assertEquals(NOTES_SERVICE.getAllNotes().size(), 4);
    }

    @Test
    @Order(2)
    public void getAllNotesTest() {
        assertFalse(NOTES_SERVICE.getAllNotes().isEmpty());
        List<Note> note = new ArrayList<>(NOTES_SERVICE.getAllNotes());
        assertFalse(note.isEmpty());
        assertEquals(note.size(), 4);
    }

    @Test
    @Order(3)
    public void getNoteByIdTest() {
        Optional<Note> note = NOTES_SERVICE.getNoteById(3);
        assertTrue(NOTES_SERVICE.getNoteById(5).isEmpty());
        assertTrue(NOTES_SERVICE.getNoteById(1).isPresent());
        assertEquals(NOTES_SERVICE.getNoteById(3), note);
    }

    @Test
    @Order(4)
    public void getNoteByNameTest() {
        Optional<Note> note = NOTES_SERVICE.getNoteByName(NOTE_NAME);
        assertTrue(NOTES_SERVICE.getNoteByName("DAYLI").isEmpty());
        assertTrue(NOTES_SERVICE.getNoteByName(NOTE_NAME).isPresent());
        assertEquals(NOTES_SERVICE.getNoteByName(NOTE_NAME), note);
    }

    @Test
    @Order(5)
    public void updateNoteTest() {
        assertTrue(true);
    }

    /*@Test
    @Order(6)
    public void deleteNoteByIdTest() {
        assertEquals(NOTES_SERVICE.getAllNotes().size(), 4);
        assertTrue(NOTES_SERVICE.deleteNoteById(2));
        assertFalse(NOTES_SERVICE.deleteNoteById(5));
        assertFalse(NOTES_SERVICE.deleteNoteById(-1));
        assertEquals(NOTES_SERVICE.getAllNotes().size(), 3);
    }

    @Test
    @Order(7)
    public void deleteNoteByNameTest() {
        assertEquals(NOTES_SERVICE.getAllNotes().size(), 3);
        assertTrue(NOTES_SERVICE.deleteNoteByName(NOTE_NAME));
        assertFalse(NOTES_SERVICE.deleteNoteByName("Katya"));
        assertFalse(NOTES_SERVICE.deleteNoteByName(""));
        assertEquals(NOTES_SERVICE.getAllNotes().size(), 2);
    }*/
}
