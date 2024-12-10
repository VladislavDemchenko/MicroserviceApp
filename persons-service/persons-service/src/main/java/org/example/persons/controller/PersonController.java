package org.example.persons.controller;

import lombok.RequiredArgsConstructor;
import org.example.persons.domain.Person;
import org.example.persons.repository.PersonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;

    @PostMapping
    public Person createPerson(@RequestBody Person person){
        return personRepository.save(person);
    }

    @GetMapping("/all")
    public List<Person> getAll(){
        return personRepository.findAll();
    }

    @GetMapping("/{personId}")
    public Person getPersonWithNotes(Long personId){

    }
}


