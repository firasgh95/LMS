package com.example.lib.service;

import com.example.lib.model.Book;
import com.example.lib.model.BorrowingRecord;
import com.example.lib.model.Patron;
import com.example.lib.repository.BookRepo;
import com.example.lib.repository.BorrowingRecordRepo;
import com.example.lib.repository.PatronRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepo borrowingRecordRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private PatronRepo patronRepo;

    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Optional<Book> bookOpt = bookRepo.findById(bookId);
        Optional<Patron> patronOpt = patronRepo.findById(patronId);

        if (bookOpt.isPresent() && patronOpt.isPresent() && !bookOpt.get().getIsBorrowed()) {
            Book book = bookOpt.get();
            Patron patron = patronOpt.get();

            book.setIsBorrowed(true);

            BorrowingRecord borrowingRecord = new BorrowingRecord();
            borrowingRecord.setBook(book);
            borrowingRecord.setPatron(patron);
            borrowingRecord.setBorrowDate(LocalDate.now());

            book.getBorrowingRecords().add(borrowingRecord);
            patron.getBorrowingRecords().add(borrowingRecord);

            return borrowingRecordRepo.save(borrowingRecord);
        } else
            throw new RuntimeException("Book or Patron not found");
    }

    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        Optional<Book> bookOpt = bookRepo.findById(bookId);
        Optional<Patron> patronOpt = patronRepo.findById(patronId);


        if (bookOpt.isPresent() && patronOpt.isPresent()) {
            Book book = bookOpt.get();
            Patron patron = patronOpt.get();

            Optional<BorrowingRecord> borrowingRecordOpt = borrowingRecordRepo.findByBookAndPatron(book, patron);
            if (borrowingRecordOpt.isPresent()) {
                BorrowingRecord borrowingRecord = borrowingRecordOpt.get();
                borrowingRecord.getBook().setIsBorrowed(false);
                borrowingRecord.setReturnDate(LocalDate.now());
                return borrowingRecordRepo.save(borrowingRecord);
            } else
                throw new RuntimeException("Borrowing record not found");


        } else
            throw new RuntimeException("Book or Patron not found");
    }
}
