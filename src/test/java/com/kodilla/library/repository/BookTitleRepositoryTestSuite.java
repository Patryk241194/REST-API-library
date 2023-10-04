package com.kodilla.library.repository;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookTitle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class BookTitleRepositoryTestSuite {

    @Autowired
    private BookTitleRepository bookTitleRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;

    private BookTitle bookTitle;

    @BeforeEach
    void setUp() {

        bookTitle = BookTitle.builder()
                .title("Sample Title")
                .author("Sample Author")
                .publicationYear(2022)
                .build();

        bookTitleRepository.save(bookTitle);
    }

    @Test
    void bookTitleRepositoryCreateTestSuite() {
        // When
        BookTitle foundBookTitle = bookTitleRepository.findById(bookTitle.getId()).orElse(null);

        // Then
        assertEquals(bookTitle, foundBookTitle);

        // CleanUp
        bookTitleRepository.deleteById(bookTitle.getId());

    }

    @Test
    void bookTitleRepositoryUpdateTestSuite() {
        // Given
        int newPublicationYear = 2019;

        // When
        bookTitle.setPublicationYear(newPublicationYear);
        bookTitleRepository.save(bookTitle);

        // Then
        BookTitle updatedBookTitle = bookTitleRepository.findById(bookTitle.getId()).orElse(null);
        assertEquals(newPublicationYear, updatedBookTitle.getPublicationYear());

        // CleanUp

    }

    @Test
    void bookTitleRepositoryDeleteTestSuite() {
        // When
        Long id = bookTitle.getId();
        bookTitleRepository.deleteById(bookTitle.getId());

        // Then
        assertFalse(bookTitleRepository.existsById(id));

    }

    @Test
    void oneToManyRelationBetweenBookTitleAndBookCopyTestSuite() {
        // Given
        BookTitle bookTitle = BookTitle.builder()
                .title("Sample Title")
                .author("Sample Author")
                .publicationYear(2022)
                .build();
        bookTitleRepository.save(bookTitle);

        BookCopy bookCopy1 = BookCopy.builder()
                .status("Available")
                .title(bookTitle)
                .build();
        bookCopyRepository.save(bookCopy1);

        BookCopy bookCopy2 = BookCopy.builder()
                .status("Available")
                .title(bookTitle)
                .build();
        bookCopyRepository.save(bookCopy2);

        // When
        bookTitle.getCopies().add(bookCopy1);
        bookTitle.getCopies().add(bookCopy2);

        // Then
        assertEquals(2, bookTitle.getCopies().size());
        assertEquals(bookCopy1.getTitle().getTitle(), bookTitle.getTitle());
        assertEquals(bookCopy2.getTitle().getTitle(), bookTitle.getTitle());

        // CleanUp
        bookTitleRepository.deleteById(bookTitle.getId());
    }
}
