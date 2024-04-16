package com.todoapp.integration.tests.repository;

import com.todoapp.model.Note;
import com.todoapp.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class NoteRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NoteRepository noteRepository;

    @Test
    public void testSaveNote() {
        Note note = new Note();
        note.setId(1L);
        note.setContent("Test content");
        note.setEmployee("Test employee");

        Note savedNote = noteRepository.save(note);

        assertEquals("Test content", savedNote.getContent());
        assertEquals("Test employee", savedNote.getEmployee());
        assertEquals(1, savedNote.getId());
        assertNotNull(savedNote.getId());

        Note noteId = new Note();
        Note savedNoteWithId = noteRepository.save(noteId);
        assertEquals(2, savedNoteWithId.getId());
    }

    @Test
    public void testFindNoteById() {
        Note note = new Note();
        note.setContent("Test content");
        note.setEmployee("Test employee");
        Note savedNote = entityManager.persistAndFlush(note);

        Optional<Note> foundNote = noteRepository.findById(savedNote.getId());

        assertTrue(foundNote.isPresent());
        assertEquals("Test content", foundNote.get().getContent());
        assertEquals("Test employee", foundNote.get().getEmployee());
    }

    @Test
    public void testUpdateNote() {
        Note note = new Note();
        note.setContent("Test content");
        note.setEmployee("Test employee");
        Note savedNote = entityManager.persistAndFlush(note);

        savedNote.setContent("Updated content");
        Note updatedNote = noteRepository.save(savedNote);

        assertEquals(savedNote.getId(), updatedNote.getId());
        assertEquals("Updated content", updatedNote.getContent());
    }

    @Test
    public void testDeleteNote() {
        Note note = new Note();
        note.setContent("Test content");
        note.setEmployee("Test employee");
        Note savedNote = entityManager.persistAndFlush(note);

        noteRepository.delete(savedNote);

        assertFalse(noteRepository.existsById(savedNote.getId()));
    }
}
