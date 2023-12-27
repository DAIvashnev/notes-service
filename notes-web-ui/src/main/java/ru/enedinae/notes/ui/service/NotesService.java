package ru.enedinae.notes.ui.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.enedinae.notes.ui.dto.NoteDto;
import ru.enedinae.notes.ui.transport.NotesBackendClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotesService {

    private final NotesBackendClient notesClient;

    public List<NoteDto> getAllNotes() {
        return notesClient.getAllNotes();
    }

    public void createNewNote(NoteDto noteDto) {
        notesClient.createNewNote(noteDto);
    }
}
