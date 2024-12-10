package org.example.notes.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notes")
@Setter
@Getter
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private Long personId;

}
