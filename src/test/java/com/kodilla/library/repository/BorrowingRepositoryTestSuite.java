package com.kodilla.library.repository;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookTitle;
import com.kodilla.library.domain.Borrowing;
import com.kodilla.library.domain.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class BorrowingRepositoryTestSuite {

    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private BookTitleRepository bookTitleRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;

    private Borrowing borrowing;

    @BeforeEach
    void setUp() {

        Reader reader = Reader.builder()
                .firstName("John")
                .lastName("Smith")
                .registrationDate(LocalDate.of(2008, 11, 24))
                .build();
        readerRepository.save(reader);

        BookTitle bookTitle = BookTitle.builder()
                .title("Sample Title")
                .author("Sample Author")
                .publicationYear(2022)
                .build();
        bookTitleRepository.save(bookTitle);

        BookCopy bookCopy = BookCopy.builder()
                .status("Available")
                .title(bookTitle)
                .build();
        bookCopyRepository.save(bookCopy);

        borrowing = Borrowing.builder()
                .borrowingDate(LocalDate.of(2022, 4, 29))
                .returnDate(LocalDate.of(2022, 8, 13))
                .reader(reader)
                .bookCopy(bookCopy)
                .build();
        borrowingRepository.save(borrowing);

    }

    @Test
    void borrowingRepositoryCreateTestSuite() {
        // When
        Borrowing foundBorrowing = borrowingRepository.findById(borrowing.getId()).orElse(null);

        // Then
        assertEquals(borrowing, foundBorrowing);

        // CleanUp
        readerRepository.deleteById(borrowing.getReader().getId());

    }

    @Test
    void borrowingRepositoryUpdateTestSuite() {
        // Given
        LocalDate newReturnDate = LocalDate.of(2022, 8, 18);

        // When
        borrowing.setReturnDate(newReturnDate);
        borrowingRepository.save(borrowing);

        // Then
        Borrowing updatedBorrowing = borrowingRepository.findById(borrowing.getId()).orElse(null);
        assertEquals(newReturnDate, updatedBorrowing.getReturnDate());

        // CleanUp
        readerRepository.deleteById(borrowing.getReader().getId());

    }

    @Test
    void borrowingRepositoryDeleteTestSuite() {
        // When
        Long id = borrowing.getId();
        Long readerId = borrowing.getReader().getId();
        borrowingRepository.deleteById(borrowing.getId());

        // Then
        assertFalse(borrowingRepository.existsById(id));

        // CleanUp
        readerRepository.deleteById(readerId);

    }
}
