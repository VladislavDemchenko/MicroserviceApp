package org.example.persons.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.persons.dto.AuthRequest;
import org.example.persons.dto.AuthResponse;
import org.example.persons.dto.PersonDto;
import org.example.persons.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<PersonDto> registerUser(@Valid @RequestBody AuthRequest authRequest) {
        log.info(authRequest.getSecretKay());
        PersonDto personDto = new PersonDto();
        personDto.setUsername(authRequest.getUsername());
        personDto.setEmail(authRequest.getEmail());
        personDto.setSecretKay(authRequest.getSecretKay());
        return new ResponseEntity<>(authService.register(personDto, authRequest.getPassword()), HttpStatus.CREATED);
    }
}