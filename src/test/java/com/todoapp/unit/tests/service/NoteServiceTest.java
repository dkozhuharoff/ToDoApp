package com.todoapp.unit.tests.service;

import com.todoapp.model.Note;
import com.todoapp.repository.NoteRepository;
import com.todoapp.exceptions.ObjectNotFoundException;
import com.todoapp.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    public void testGetNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note());
        when(noteRepository.findAll()).thenReturn(notes);

        List<Note> result = noteService.getNotes();

        assertEquals(1, result.size());
    }

    @Test
    public void testGetNotesEmpty() {
        when(noteRepository.findAll()).thenReturn(null);

        List<Note> result = noteService.getNotes();

        assertEquals(0, result.size());
    }

    @Test
    public void testAddNote() {
        Note note = new Note();
        when(noteRepository.save(note)).thenReturn(note);

        Note result = noteService.addNote(note);

        assertNotNull(result);
    }

    @Test
    public void testUpdateNote() {
        try {
            Note existingNote = new Note(1L, "Random content", "Random employee");
            Note updatedNote = new Note(1L, "Updated content", "Updated employee");

            when(noteRepository.findById(existingNote.getId())).thenReturn(Optional.of(existingNote));
            existingNote.setContent(updatedNote.getContent());
            existingNote.setEmployee(updatedNote.getEmployee());
            when(noteRepository.save(existingNote)).thenReturn(existingNote);
            Note result = noteService.updateNote(updatedNote.getId(), updatedNote);

            assertEquals("Updated content", result.getContent());
            assertEquals("Updated employee", result.getEmployee());
        } catch (ObjectNotFoundException e) {
            fail("ObjectNotFoundException should not be thrown");
        }
    }

    @Test
    public void testUpdateNoteNotFound() {
        Long id = 1L;
        when(noteRepository.findById(id)).thenReturn(Optional.empty());
        Note updatedNote = new Note();

        assertThrows(ObjectNotFoundException.class, () -> noteService.updateNote(id, updatedNote));
    }

    @Test
    public void testDeleteNoteById() {
        Long id = 1L;
        when(noteRepository.findById(id)).thenReturn(Optional.of(new Note()));

        assertDoesNotThrow(() -> noteService.deleteNoteById(id));
        verify(noteRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteNoteByIdNotFound() {
        Long id = 1L;
        when(noteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> noteService.deleteNoteById(id));
        verify(noteRepository, never()).deleteById(id);
    }
}
