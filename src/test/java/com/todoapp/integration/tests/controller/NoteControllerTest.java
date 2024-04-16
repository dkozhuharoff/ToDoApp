package com.todoapp.integration.tests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todoapp.model.Note;
import com.todoapp.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Test
    public void testGetNotes() throws Exception {
        when(noteService.getNotes()).thenReturn(Collections.singletonList(new Note("test", "test")));

        mockMvc.perform(get("/api/v1/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testAddNote() throws Exception {
        String content = "Test content";
        String employee = "Test employee";
        Note note = new Note(content, employee);
        when(noteService.addNote(any())).thenReturn(note);

        mockMvc.perform(post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").value("Test content"))
                .andExpect(jsonPath("$.employee").value("Test employee"));
    }

    @Test
    public void testUpdateNote() throws Exception {
        Long id = 1L;
        String content = "Updated content";
        String employee = "Updated employee";
        Note updatedNote = new Note(content, employee);
//        when(noteService.updateNote(id, updatedNote)).thenReturn(updatedNote);
        when(noteService.updateNote(any(), any())).thenReturn(updatedNote);

        mockMvc.perform(put("/api/v1/notes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedNote)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").value("Updated content"))
                .andExpect(jsonPath("$.employee").value("Updated employee"));
    }

    @Test
    public void testDeleteNote() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/v1/notes/{id}", id))
                .andExpect(status().isOk());
    }
}
