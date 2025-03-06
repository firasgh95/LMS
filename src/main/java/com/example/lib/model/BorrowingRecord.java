package com.example.lib.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="borrowing_record")
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name="patron_id", nullable = false)
    private Patron patron;

    @Column(name="borrow_date")
    private LocalDate borrowDate;

    @Column(name="return_date")
    private LocalDate returnDate;
}
