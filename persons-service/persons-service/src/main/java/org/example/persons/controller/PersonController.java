package org.example.persons.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.persons.domain.Person;
import org.example.persons.dto.Message;
import org.example.persons.dto.NoteDto;
import org.example.persons.dto.PersonDto;
import org.example.persons.repository.PersonRepository;
import org.example.persons.service.PersonService;
import org.postgresql.translation.messages_bg;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/persons")
public class PersonController {

    private final RabbitTemplate rabbitTemplate;
    private final PersonRepository personRepository;
    private final PersonService personService;

    @PostMapping
    public void postMassage(@RequestBody Message message){
        log.info("Received HTTP request with message: '{}'", message.body());
        rabbitTemplate.convertAndSend("message-fanout", "", message.body());
    }

    @PostMapping("/create")
    public Person createPerson(@RequestBody Person person){
        return personRepository.save(person);
    }

    @GetMapping("/all")
    public List<Person> getAll(){
        return personRepository.findAll();
    }

    @GetMapping("/personWithNotes/{personId}")
    public PersonDto getPersonWithNotes(@PathVariable Long personId){
        return personService.getWithNoteById(personId);
    }

    @PutMapping("/{id}")
    public void updatePerson(@RequestBody Person person){
        personService.updatePerson(person);
    }

    @GetMapping("/{id}")
    public Person getOne(@PathVariable Long id){
        return personRepository.findById(id)
                .orElseThrow();
    }
}


