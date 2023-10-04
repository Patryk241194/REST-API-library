package com.kodilla.library.repository;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookTitle;
import com.kodilla.library.domain.Borrowing;
import com.kodilla.library.domain.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class BookCopyRepositoryTestSuite {

    @Autowired
    private BookCopyRepository bookCopyRepository;
    @Autowired
    private BookTitleRepository bookTitleRepository;
    private BookCopy bookCopy;

    @BeforeEach
    void setUp() {

        BookTitle bookTitle = BookTitle.builder()
                .title("Sample Title")
                .author("Sample Author")
                .publicationYear(2022)
                .build();
        bookTitleRepository.save(bookTitle);

        bookCopy = BookCopy.builder()
                .status("Available")
                .title(bookTitle)
                .build();
        bookCopyRepository.save(bookCopy);

    }

    @Test
    void bookCopyRepositoryCreateTestSuite() {
        // When
        BookCopy foundBookCopy = bookCopyRepository.findById(bookCopy.getId()).orElse(null);

        // Then
        assertEquals(bookCopy, foundBookCopy);

        // CleanUp
        bookTitleRepository.deleteById(bookCopy.getId());

    }

    @Test
    void bookCopyRepositoryUpdateTestSuite() {
        // Given
        String newStatus = "Borrowed";

        // When
        bookCopy.setStatus(newStatus);
        bookCopyRepository.save(bookCopy);

        // Then
        BookCopy updatedBookCopy = bookCopyRepository.findById(bookCopy.getId()).orElse(null);
        assertEquals(newStatus, updatedBookCopy.getStatus());

        // CleanUp
        bookTitleRepository.deleteById(bookCopy.getId());

    }

    @Test
    void bookCopyRepositoryDeleteTestSuite() {
        // When
        Long id = bookCopy.getId();
        bookCopyRepository.deleteById(bookCopy.getId());

        // Then
        assertFalse(bookCopyRepository.existsById(id));

        // CleanUp
        bookTitleRepository.deleteById(bookCopy.getId());

    }
}
