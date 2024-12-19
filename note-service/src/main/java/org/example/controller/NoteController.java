package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.Note;
import org.example.dto.NoteDto;
import org.example.service.NoteService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    public Note crate(@RequestBody Note note){
        rabbitTemplate.convertAndSend("note-fanout", "", note);
        return noteService.save(note);
    }

    @GetMapping
    public List<NoteDto> getAll(){
        return noteService.getAllWithPersons();
    }

    @GetMapping("/{personId}")
    public List<Note> getNotesByPerson(Long personId){
        return noteService.getAllByPerson(personId);
    }

}
