package com.example.lib.controller;

import com.example.lib.model.Book;
import com.example.lib.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Book book;
    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setPublicationYear(2021);
    }

    @Test
    void testGetBooks() throws Exception {
        // Given
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");
        book1.setAuthor("Author 1");
        book1.setPublicationYear(2020);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
        book2.setAuthor("Author 2");
        book2.setPublicationYear(2021);

        when(bookService.findAll()).thenReturn(Arrays.asList(book1, book2));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].title").value("Book 2"));
    }

    @Test
    void testGetBookById() throws Exception {
        when(bookService.findById(book.getId())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Author"));

    }

    @Test
    void testAddBook() throws Exception{
        when(bookService.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Test Book\",\"author\":\"Author\",\"publicationYear\":2021}"))
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Author"));
    }

    @Test
    void testEditBook() throws Exception{
        // Given
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setPublicationYear(2022);

        when(bookService.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookService.save(any(Book.class))).thenReturn(updatedBook);

        // When & Then
        mockMvc.perform(put("/api/books/edit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Title\",\"author\":\"Updated Author\",\"publicationYear\":2022}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.author").value("Updated Author"));
    }

    @Test
    void testDeleteBook() throws Exception{
        when(bookService.existsById(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/books/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

    }
}