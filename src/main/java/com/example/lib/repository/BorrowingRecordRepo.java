package com.example.lib.repository;

import com.example.lib.model.Book;
import com.example.lib.model.BorrowingRecord;
import com.example.lib.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepo extends JpaRepository<BorrowingRecord, Long> {
    Optional<BorrowingRecord> findByBookAndPatron(Book book, Patron patron);
}
