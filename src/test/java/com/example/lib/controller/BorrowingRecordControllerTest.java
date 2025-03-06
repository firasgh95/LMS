package com.example.lib.controller;

import com.example.lib.controller.BorrowingRecordController;
import com.example.lib.model.Book;
import com.example.lib.model.BorrowingRecord;
import com.example.lib.model.Patron;
import com.example.lib.service.BorrowingRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BorrowingRecordController.class)
class BorrowingRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    private Book book;
    private Patron patron;
    private BorrowingRecord borrowingRecord;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setPublicationYear(2021);
        book.setIsBorrowed(false);

        patron = new Patron();
        patron.setId(1L);
        patron.setName("Test Patron");
        patron.setContactInfo(123);

        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());
    }

    @Test
    void testBorrowBook() throws Exception {
        when(borrowingRecordService.borrowBook(eq(1L), eq(1L))).thenReturn(borrowingRecord);

        mockMvc.perform(post("/api/borrow/1/patron/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.book.title").value("Test Book"))
                .andExpect(jsonPath("$.patron.name").value("Test Patron"));
    }

    @Test
    void testReturnBook() throws Exception{
        when(borrowingRecordService.returnBook(eq(1L), eq(1L))).thenReturn(borrowingRecord);

        mockMvc.perform(put("/api/return/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.book.title").value("Test Book"))
                .andExpect(jsonPath("$.patron.name").value("Test Patron"));
    }
}