package ru.enedinae.notes.ui.transport;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.enedinae.notes.ui.dto.NoteDto;

import java.util.List;

@FeignClient(value = "notesClient", url = "http://localhost:8080")
public interface NotesBackendClient {

    @RequestMapping(method = RequestMethod.GET, value = "/notes")
    List<NoteDto> getAllNotes();

    @RequestMapping(method = RequestMethod.POST, value = "/notes/createNote")
    void createNewNote(NoteDto noteDto);
}
