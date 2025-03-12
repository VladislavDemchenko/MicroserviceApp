package org.example.persons.service;


import org.example.persons.dto.AuthRequest;
import org.example.persons.dto.AuthResponse;
import org.example.persons.dto.PersonDto;

public interface AuthService {

    AuthResponse authenticate(AuthRequest authRequest);

    PersonDto register(PersonDto personDto, String password);
}
