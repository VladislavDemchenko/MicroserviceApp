package org.example.persons.dto;

import java.util.List;

public record PersonDto (String firstName, String lastName, List<NoteDto> notes){
}
