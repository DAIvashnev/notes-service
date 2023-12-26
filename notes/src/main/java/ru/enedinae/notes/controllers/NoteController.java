package ru.enedinae.notes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.enedinae.notes.exceptions.NotCorrectFormatDataException;
import ru.enedinae.notes.exceptions.NotHaveNotesException;
import ru.enedinae.notes.exceptions.NoteNotFoundException;
import ru.enedinae.notes.entity.Note;
import ru.enedinae.notes.repository.NoteRepository;
import ru.enedinae.notes.service.NotesService;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NotesService notesService;
    @Autowired
    public NoteController(NotesService notesService, NoteRepository noteRepository) {
        this.notesService = notesService;
    }

    @GetMapping
    public ResponseEntity getAllNotes() {
        try {
            return ResponseEntity.ok(notesService.getAllNotes());
        } catch (NotHaveNotesException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("createNote")
    public ResponseEntity createNote(@RequestBody Note note) {
        try {
            notesService.createNote(note.getName(),note.getDescription(),note.getDeadline());
            return ResponseEntity.ok("Заметка успешно создана");
        } catch (NotCorrectFormatDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getNoteById")
    public ResponseEntity getNoteById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(notesService.getNoteById(id));
        } catch (NoteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getNoteByName")
    public ResponseEntity getNoteByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok(notesService.getNoteByName(name));
        } catch (NoteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/deleteNoteById")
    public ResponseEntity deleteNoteById(@RequestParam Long id) {
        try {
            notesService.deleteNoteById(id);
            return ResponseEntity.ok("Заметка успешно удалена");
        } catch (NoteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateNoteById")
    public ResponseEntity updateNoteById(@RequestParam Long id, @RequestBody Note note) {
        try {
            notesService.updateNote(id, note);
            return ResponseEntity.ok("Заметка успешно обновлена");
        } catch (NoteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotCorrectFormatDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
