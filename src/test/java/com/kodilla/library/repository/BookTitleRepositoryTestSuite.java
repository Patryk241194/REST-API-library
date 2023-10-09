package com.kodilla.library.repository;

import com.kodilla.library.domain.bookcopy.BookCopy;
import com.kodilla.library.domain.booktitle.BookTitle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static com.kodilla.library.domain.bookcopy.CopyStatus.AVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
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
                .build();

        bookTitleRepository.save(bookTitle);
    }

    @Test
    void bookTitleRepositoryCreateTestSuite() {
        // When
        BookTitle foundBookTitle = bookTitleRepository.findById(bookTitle.getId()).orElse(null);

        // Then
        assertEquals(bookTitle, foundBookTitle);


    }

    @Test
    void bookTitleRepositoryUpdateTestSuite() {
        // Given
        String newTitle = "Updated Title";

        // When
        bookTitle.setTitle(newTitle);
        bookTitleRepository.save(bookTitle);

        // Then
        BookTitle updatedBookTitle = bookTitleRepository.findById(bookTitle.getId()).orElse(null);
        assertEquals(newTitle, updatedBookTitle.getTitle());

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

        BookCopy bookCopy1 = BookCopy.builder()
                .status(AVAILABLE)
                .publicationYear(2022)
                .title(bookTitle)
                .build();
        bookCopyRepository.save(bookCopy1);

        BookCopy bookCopy2 = BookCopy.builder()
                .status(AVAILABLE)
                .publicationYear(2019)
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

    }

}
