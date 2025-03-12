package org.example.persons.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.persons.client.NoteServiceClient;
import org.example.persons.domain.Person;
import org.example.persons.dto.PersonDto;
import org.example.persons.exception.ResourceNotFoundException;
import org.example.persons.repository.PersonRepository;
import org.example.persons.service.PersonService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final NoteServiceClient noteServiceClient;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Override
    public List<PersonDto> getAllPersons() {
        return personRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDto getPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
        return convertToDto(person);
    }

    @Override
    public PersonDto getPersonByUsername(String username) {
        Person person = personRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with username: " + username));
        return convertToDto(person);
    }

    @Override
    @Transactional
    public PersonDto updatePerson(Long id, PersonDto personDto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        if (personDto.getUsername() != null && !personDto.getUsername().equals(person.getUsername())) {
            if (personRepository.existsByUsername(personDto.getUsername())) {
                throw new RuntimeException("Username is already taken");
            }
            person.setUsername(personDto.getUsername());
        }

        if (personDto.getEmail() != null && !personDto.getEmail().equals(person.getEmail())) {
            if (personRepository.existsByEmail(personDto.getEmail())) {
                throw new RuntimeException("Email is already in use");
            }
            person.setEmail(personDto.getEmail());
        }

        if (personDto.getFirstName() != null) {
            person.setFirstName(personDto.getFirstName());
        }

        if (personDto.getLastName() != null) {
            person.setLastName(personDto.getLastName());
        }

        if (personDto.getRoles() != null) {
            person.setRoles(personDto.getRoles());
        }

        Person updatedPerson = personRepository.save(person);
        return convertToDto(updatedPerson);
    }

    @Override
    @Transactional
    public void deletePerson(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));

        try {
            // Використовуємо Feign Client для синхронного видалення нотаток
            noteServiceClient.deleteNotesByPersonId(id);
        } catch (Exception e) {
            log.error("Error when trying to delete notes via Feign client", e);

            // Якщо синхронний запит не вдався, використовуємо RabbitMQ для асинхронного видалення
            rabbitTemplate.convertAndSend(exchange, routingKey, id);
            log.info("Sent delete request via RabbitMQ for person with id: {}", id);
        }

        personRepository.deleteById(id);
    }

    private PersonDto convertToDto(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setUsername(person.getUsername());
        personDto.setEmail(person.getEmail());
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setRoles(person.getRoles());
        return personDto;
    }
}
