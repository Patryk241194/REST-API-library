package com.kodilla.library.repository;

import com.kodilla.library.domain.bookcopy.BookCopy;
import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.domain.booktitle.BookTitle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static com.kodilla.library.domain.bookcopy.CopyStatus.AVAILABLE;
import static com.kodilla.library.domain.bookcopy.CopyStatus.BORROWED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
class BookCopyRepositoryTestSuite {

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
                .build();
        bookTitleRepository.save(bookTitle);

        bookCopy = BookCopy.builder()
                .status(AVAILABLE)
                .publicationYear(2022)
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

    }

    @Test
    void bookCopyRepositoryUpdateTestSuite() {
        // Given
        CopyStatus newStatus = BORROWED;

        // When
        bookCopy.setStatus(newStatus);
        bookCopyRepository.save(bookCopy);

        // Then
        BookCopy updatedBookCopy = bookCopyRepository.findById(bookCopy.getId()).orElse(null);
        assert updatedBookCopy != null;
        assertEquals(newStatus, updatedBookCopy.getStatus());

    }

    @Test
    void bookCopyRepositoryDeleteTestSuite() {
        // When
        Long id = bookCopy.getId();
        bookCopyRepository.deleteById(bookCopy.getId());

        // Then
        assertFalse(bookCopyRepository.existsById(id));

    }

}
