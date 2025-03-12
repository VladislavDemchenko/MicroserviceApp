package org.example.service;

import org.example.dto.NoteDto;

import java.util.List;

public interface NoteService {

    List<NoteDto> getAllNotes();

    NoteDto getNoteById(Long id);

    List<NoteDto> getNotesByPersonId(Long personId);

    NoteDto createNote(NoteDto noteDto);

    NoteDto updateNote(Long id, NoteDto noteDto);

    void deleteNote(Long id);

    void deleteNotesByPersonId(Long personId);
}
