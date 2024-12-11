package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.client.PersonServiceFeignClient;
import org.example.domain.Note;
import org.example.dto.NoteDto;
import org.example.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final PersonServiceFeignClient personServiceFeignClient;

    public List<NoteDto> getAllWithPersons() {
        return noteRepository.findAll()
                .stream()
                .map(n -> coverToDto(n))
                .toList();
    }

    private NoteDto coverToDto(Note note) {
        var person = personServiceFeignClient.getById(note.getPersonId());
        return new NoteDto(note.getId(), note.getBody(), person);
    }
}
