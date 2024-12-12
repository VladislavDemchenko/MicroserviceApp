package org.example.persons.client;

import org.example.persons.dto.NoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "notes-service")
public interface NotesClient {

    @GetMapping("/api/notes/{personId}")
    List<NoteDto> getNotesByPersonId(@RequestParam @PathVariable Long personId);

}
