package ru.enedinae.notesTest;

import org.junit.Test;
import ru.enedinae.notes.model.Note;
import ru.enedinae.notes.service.NotesService;
import ru.enedinae.notes.service.impl.InMemoryNotesServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class InMemoryNotesServiceImplTest {
    private final NotesService notesService = new InMemoryNotesServiceImpl();

    @Test
    public void createNoteTest() {
        notesService.createNote("name","desc","deadline");
        assertFalse(notesService.getAllNotes().isEmpty());
        assertEquals(notesService.getAllNotes().size(), 1);
    }

    @Test
    public void deleteNoteByIdTest() {
        notesService.createNote("name","desc","deadline");
        notesService.createNote("name","desc","deadline");
        notesService.createNote("name","desc","deadline");
        Integer a = 0;
        System.out.println(notesService.getAllNotes().size());
        assertTrue(notesService.deleteNoteById(a));
        /*assertTrue(notesService.deleteNoteById(2));
        assertFalse(notesService.deleteNoteById(3));
        assertFalse(notesService.deleteNoteById(-1));*/
    }

    @Test
    public void deleteNoteByNameTest() {
        notesService.createNote("Dima","desc","deadline");
        notesService.createNote("Vlad","desc","deadline");
        notesService.createNote("Katya","desc","deadline");
        assertTrue(notesService.deleteNoteByName("Dima"));
        assertTrue(notesService.deleteNoteByName("Katya"));
        assertFalse(notesService.deleteNoteByName("Dima"));
        assertFalse(notesService.deleteNoteByName(""));
    }

    @Test
    public void getAllNotesTest() {
        assertTrue(notesService.getAllNotes().isEmpty());
        notesService.createNote("name","desc","deadline");
        notesService.createNote("name","desc","deadline");
        List<Note> note = new ArrayList<>(notesService.getAllNotes());
        assertFalse(note.isEmpty());
        assertEquals(note.size(), 2);
    }

    @Test
    public void getNoteByIdTest() {
        notesService.createNote("Dima","desc","deadline");
        notesService.createNote("Vlad","desc","deadline");
        notesService.createNote("Katya","desc","deadline");
        assertTrue(notesService.getNoteById(5).isEmpty());
        assertTrue(notesService.getNoteById(1).isPresent());
    }
}
