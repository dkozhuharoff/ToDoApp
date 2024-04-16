package com.todoapp.controller;

import com.todoapp.exceptions.ObjectNotFoundException;
import com.todoapp.model.Note;
import com.todoapp.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/notes")
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<?> getNotes() {
        List<Note> notes = noteService.getNotes();

        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addNote(@RequestBody Note note) {
        try {
            Note newNote = noteService.addNote(note);

            return new ResponseEntity<>(newNote, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("The creation of the note was not successful.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable long id, @RequestBody Note updatedNote) {
        try {
            Note newNote = noteService.updateNote(id, updatedNote);

            return new ResponseEntity<>(newNote, HttpStatus.OK);
        } catch (ObjectNotFoundException | RuntimeException  e) {
            return new ResponseEntity<>("The updating of the note was not successful.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable long id) {
        try {
            noteService.deleteNoteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ObjectNotFoundException | RuntimeException e) {
            return new ResponseEntity<>("The deletion of note with ID " + id + " was not successful.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
