package com.todoapp.unit.tests.model;

import com.todoapp.model.Note;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoteTest {

    @Test
    public void testNoteConstructorAndGetters() {
        String content = "Test content";
        String employee = "Test employee";

        Note note = new Note(content, employee);

        assertEquals(content, note.getContent());
        assertEquals(employee, note.getEmployee());
    }
}
