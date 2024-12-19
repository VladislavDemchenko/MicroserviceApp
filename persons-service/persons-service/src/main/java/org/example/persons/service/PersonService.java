package org.example.persons.service;

import com.netflix.discovery.DiscoveryClient;
import lombok.RequiredArgsConstructor;
import org.example.persons.client.NotesClient;
import org.example.persons.domain.Person;
import org.example.persons.dto.PersonDto;
import org.example.persons.event.PersonUpdatedEvent;
import org.example.persons.repository.PersonRepository;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final NotesClient notesClient;
    private final StreamBridge streamBridge;

    public PersonDto getWithNoteById(Long personId){
        var person = personRepository.findById(personId)
                .orElseThrow();
        var notes = notesClient.getNotesByPersonId(personId);
        return new PersonDto(person.getFirstName(), person.getLastName(), notes);
    }

    public void updatePerson(Person person) {
        personRepository.save(person);
        streamBridge.send("persons-topic", new PersonUpdatedEvent(person.getId()));
    }

}
