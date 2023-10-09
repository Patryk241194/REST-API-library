package com.kodilla.library.repository;

import com.kodilla.library.domain.bookcopy.BookCopy;
import com.kodilla.library.domain.booktitle.BookTitle;
import com.kodilla.library.domain.borrowing.Borrowing;
import com.kodilla.library.domain.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static com.kodilla.library.domain.bookcopy.CopyStatus.AVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
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
                .build();
        bookTitleRepository.save(bookTitle);

        BookCopy bookCopy = BookCopy.builder()
                .status(AVAILABLE)
                .publicationYear(2022)
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

    }

    @Test
    void borrowingRepositoryDeleteTestSuite() {
        // When
        Long id = borrowing.getId();
        Long readerId = borrowing.getReader().getId();
        borrowingRepository.deleteById(id);

        // Then
        assertFalse(borrowingRepository.existsById(id));

    }

    @Test
    void cleanUp() {
        readerRepository.deleteAll();
        bookTitleRepository.deleteAll();
    }

}


