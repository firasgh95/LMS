package com.example.lib.controller;

import com.example.lib.model.Patron;
import com.example.lib.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(PatronController.class)
class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    private Patron patron;

    @BeforeEach
    void setUp() {
        patron = new Patron();
        patron.setId(1L);
        patron.setName("Test Patron");
        patron.setContactInfo(123);
    }

    @Test
    void testGetPatrons() throws Exception{
        Patron patron1 = new Patron();
        patron1.setId(1L);
        patron1.setName("Patron 1");
        patron1.setContactInfo(123);

        Patron patron2 = new Patron();
        patron2.setId(2L);
        patron2.setName("Patron 2");
        patron2.setContactInfo(456);

        when(patronService.findAll()).thenReturn(Arrays.asList(patron1, patron2));

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Patron 1"))
                .andExpect(jsonPath("$[1].name").value("Patron 2"));
    }

    @Test
    void getPatronById() throws Exception {
        when(patronService.findById(anyLong())).thenReturn(Optional.of(patron));

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Patron"))
                .andExpect(jsonPath("$.contactInfo").value(123));
    }

    @Test
    void testAddPatron() throws Exception {
        when(patronService.save(any(Patron.class))).thenReturn(patron);

        mockMvc.perform(post("/api/patrons/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Patron\",\"contactInfo\":\"123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Patron"))
                .andExpect(jsonPath("$.contactInfo").value(123));
    }

    @Test
    void testEditPatron() throws Exception {
        Patron updatedPatron = new Patron();
        updatedPatron.setId(1L);
        updatedPatron.setName("Updated Patron");
        updatedPatron.setContactInfo(456);

        when(patronService.findById(anyLong())).thenReturn(Optional.of(patron));
        when(patronService.save(any(Patron.class))).thenReturn(updatedPatron);

        mockMvc.perform(put("/api/patrons/edit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Patron\",\"contactInfo\":\"456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Patron"))
                .andExpect(jsonPath("$.contactInfo").value(456));
    }

    @Test
    void testDeletePatron() throws Exception{
        when(patronService.existsById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/patrons/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}