package org.example.persons.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "notes-service", url = "${note-service.url}")
public interface NoteServiceClient {

    @GetMapping("/api/notes/person/{personId}")
    Object getNotesByPersonId(@PathVariable("personId") Long personId);

    @DeleteMapping("/api/notes/person/{personId}")
    void deleteNotesByPersonId(@PathVariable("personId") Long personId);
}
