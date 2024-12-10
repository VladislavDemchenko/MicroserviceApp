package org.example.notes.repository;

import org.example.notes.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<List<Note>> findNoteByPersonId(Long personId);
}
