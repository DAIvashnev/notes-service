package ru.enedinae.notes.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.enedinae.notes.ui.dto.NoteDto;
import ru.enedinae.notes.ui.service.NotesService;

@Controller
@RequiredArgsConstructor
public class WebInterfaceController {

    private final NotesService notesService;

    @GetMapping("/index")
    public String showAllNotes(Model model) {
        model.addAttribute("notes", notesService.getAllNotes());
        return "index";
    }

    @GetMapping("/newNote")
    public String createNewNote() {
        return "new-note";
    }

    @RequestMapping("/createNewNote")
    public String createNewNote(@RequestParam(value = "noteName") String noteName, @RequestParam(value = "noteDesc") String noteDesc, Model model) {
        System.out.println(noteName);
        System.out.println(noteDesc);
        notesService.createNewNote(new NoteDto(null, noteName, noteDesc, "", null));
        return "redirect:/index";
    }
}
