package org.example.persons.service;


import org.example.persons.dto.PersonDto;

import java.util.List;

public interface PersonService {

    List<PersonDto> getAllPersons();

    PersonDto getPersonById(Long id);

    PersonDto getPersonByUsername(String username);

    PersonDto updatePerson(Long id, PersonDto personDto);

    void deletePerson(Long id);
}
