package com.example.ToDoApp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Note {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String content;

    @NonNull
    private String employee;
}
