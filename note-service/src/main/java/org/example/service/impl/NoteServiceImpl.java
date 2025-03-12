package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.domain.Note;
import org.example.dto.NoteDto;
import org.example.exception.ResourceNotFoundException;
import org.example.repository.NoteRepository;
import org.example.service.NoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public List<NoteDto> getAllNotes() {
        return noteRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoteDto getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        return convertToDto(note);
    }

    @Override
    public List<NoteDto> getNotesByPersonId(Long personId) {
        return noteRepository.findByPersonId(personId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NoteDto createNote(NoteDto noteDto) {
        Note note = new Note();
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        note.setPersonId(noteDto.getPersonId());

        Note savedNote = noteRepository.save(note);
        return convertToDto(savedNote);
    }

    @Override
    @Transactional
    public NoteDto updateNote(Long id, NoteDto noteDto) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));

        if (noteDto.getTitle() != null) {
            note.setTitle(noteDto.getTitle());
        }

        if (noteDto.getContent() != null) {
            note.setContent(noteDto.getContent());
        }

        note.setUpdatedAt(LocalDateTime.now());
        Note updatedNote = noteRepository.save(note);

        return convertToDto(updatedNote);
    }

    @Override
    @Transactional
    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Note not found with id: " + id);
        }

        noteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteNotesByPersonId(Long personId) {
        noteRepository.deleteByPersonId(personId);
    }

    private NoteDto convertToDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getCreatedAt(),
                note.getUpdatedAt(),
                note.getPersonId()
        );
    }
}
