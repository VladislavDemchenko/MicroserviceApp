package org.example.persons.service;

import com.netflix.discovery.DiscoveryClient;
import lombok.RequiredArgsConstructor;
import org.example.persons.client.NotesClient;
import org.example.persons.dto.PersonDto;
import org.example.persons.repository.PersonRepository;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
//    private final DiscoveryClient discoveryClient;
    private final NotesClient notesClient;


    public PersonDto getWithNoteById(Long personId){
        var person = personRepository.findById(personId)
                .orElseThrow();
        var notes = notesClient.getNoteByPersonId(personId);
        return new PersonDto(person.getFirstName(), person.getLastName(), notes);
    }
}
