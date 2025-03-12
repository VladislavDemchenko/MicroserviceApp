package org.example.persons.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.persons.domain.Person;
import org.example.persons.dto.AuthRequest;
import org.example.persons.dto.AuthResponse;
import org.example.persons.dto.PersonDto;
import org.example.persons.exception.AlreadyExistException;
import org.example.persons.repository.PersonRepository;
import org.example.persons.security.JwtTokenProvider;
import org.example.persons.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.secret}")
    private String UNDERSECRETARY;

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);

        return new AuthResponse(jwt, "Bearer", authRequest.getUsername());
    }

    @Override
    @Transactional
    public PersonDto register(PersonDto personDto, String password) {
		log.info(personDto.getSecretKay());
        if (personRepository.existsByUsername(personDto.getUsername())) {
            throw new AlreadyExistException("Username is already taken");
        }

        if (personRepository.existsByEmail(personDto.getEmail())) {
            throw new AlreadyExistException("Email is already in use");
        }

        Person person = new Person();
        person.setUsername(personDto.getUsername());
        person.setEmail(personDto.getEmail());
        person.setPassword(passwordEncoder.encode(password));
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());


        log.info(personDto.getSecretKay());
        log.info(UNDERSECRETARY);
		log.info(personDto.getSecretKay().equals(UNDERSECRETARY));
        if (personDto.getSecretKay().equals(UNDERSECRETARY)) {
            person.setRoles(new HashSet<>(Collections.singletonList("ADMIN")));
        } else {
            person.setRoles(new HashSet<>(Collections.singletonList("ROLE_USER")));
        }

        Person savedPerson = personRepository.save(person);

        PersonDto savedPersonDto = new PersonDto();
        savedPersonDto.setId(savedPerson.getId());
        savedPersonDto.setUsername(savedPerson.getUsername());
        savedPersonDto.setEmail(savedPerson.getEmail());
        savedPersonDto.setFirstName(savedPerson.getFirstName());
        savedPersonDto.setLastName(savedPerson.getLastName());
        savedPersonDto.setRoles(savedPerson.getRoles());

        return savedPersonDto;
    }
}
