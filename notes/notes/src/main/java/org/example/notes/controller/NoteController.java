package org.example.notes.controller;


import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.notes.domain.Note;
import org.example.notes.repository.NoteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteRepository noteRepository;

    @GetMapping
    public List<Note> getNoteByPersonId(@RequestParam Long personId){
        return noteRepository.findNoteByPersonId(personId)
                .orElseThrow(() -> new NotFoundException("not found notes with personId " + personId));
    }

    @PostMapping
    public Note createNote(@RequestBody Note note){
        return noteRepository.save(note);
    }
}
