package org.example.persons.controller;

import lombok.RequiredArgsConstructor;
import org.example.persons.domain.Person;
import org.example.persons.dto.PersonDto;
import org.example.persons.repository.PersonRepository;
import org.example.persons.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;
    private final PersonService personService;

    @PostMapping
    public Person createPerson(@RequestBody Person person){
        return personRepository.save(person);
    }

    @GetMapping("/all")
    public List<Person> getAll(){
        return personRepository.findAll();
    }

    @GetMapping("/{personId}")
    public PersonDto getPersonWithNotes(@PathVariable Long personId){
        return personService.getWithNoteById(personId);
    }

    @GetMapping("/{id}")
    public Person getOne(@PathVariable Long id){
        return personRepository.findById(id)
                .orElseThrow();
    }
}


