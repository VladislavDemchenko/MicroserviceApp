package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.NoteDto;
import org.example.service.NoteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

//    private final NoteService noteService;

//    public List<NoteDto> getAll(){
//        return noteService.getAllWithPersons();
//    }
}
