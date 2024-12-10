package org.example.persons.client;

import org.example.persons.dto.NoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "notes")
public interface NotesClient {

    @GetMapping("/notes")
    List<NoteDto> getNoteByPersonId(@RequestParam Long personId);

}
