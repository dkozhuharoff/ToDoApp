package com.todoapp.service;

import com.todoapp.model.Note;
import com.todoapp.repository.NoteRepository;
import com.todoapp.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository toDoAppRepository;

    @Autowired
    public NoteService(NoteRepository toDoAppRepository) {
        this.toDoAppRepository = toDoAppRepository;
    }

    public List<Note> getNotes() {
        List<Note> notes = toDoAppRepository.findAll();

        if (notes == null) {
            return Collections.emptyList();
        }

        return notes;
    }

    public Note addNote(Note note) {
        Note newNote = toDoAppRepository.save(note);

        return newNote;
    }

    public Note updateNote(Long id, Note updatedNote) throws ObjectNotFoundException {
        Note note = toDoAppRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("This object was not found."));

        note.setId(id);
        note.setContent(updatedNote.getContent());
        note.setEmployee(updatedNote.getEmployee());

        note = toDoAppRepository.save(note);

        return note;
    }

    public void deleteNoteById(Long id) throws ObjectNotFoundException {
        toDoAppRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("This object was not found."));
        toDoAppRepository.deleteById(id);
    }
}