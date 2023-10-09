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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ReaderRepositoryTestSuite {

    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private BorrowingRepository borrowingRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;
    @Autowired
    private BookTitleRepository bookTitleRepository;
    private Reader reader;

    @BeforeEach
    void setUp() {

        reader = Reader.builder()
                .firstName("John")
                .lastName("Smith")
                .registrationDate(LocalDate.of(2008, 11, 24))
                .build();

        readerRepository.save(reader);
    }

    @Test
    void readerRepositoryCreateTestSuite() {
        // When
        Reader foundReader = readerRepository.findById(reader.getId()).orElse(null);

        // Then
        assertEquals(reader, foundReader);

    }

    @Test
    void readerRepositoryUpdateTestSuite() {
        // Given
        LocalDate newCreatedDate = LocalDate.of(2008, 10, 21);

        // When
        reader.setRegistrationDate(newCreatedDate);
        readerRepository.save(reader);

        // Then
        Reader updatedReader = readerRepository.findById(reader.getId()).orElse(null);
        assertEquals(newCreatedDate, updatedReader.getRegistrationDate());

    }

    @Test
    void readerRepositoryDeleteTestSuite() {
        // When
        Long id = reader.getId();
        readerRepository.deleteById(reader.getId());

        // Then
        assertFalse(readerRepository.existsById(id));

    }

    @Test
    void oneToManyRelationBetweenReaderAndBorrowingTestSuite() {
        // Given
        BookTitle bookTitle = BookTitle.builder()
                .title("Sample Title")
                .author("Sample Author")
                .build();
        bookTitleRepository.save(bookTitle);

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

        Borrowing borrowing1 = Borrowing.builder()
                .borrowingDate(LocalDate.of(2023, 9, 29))
                .returnDate(LocalDate.of(2023, 10, 10))
                .reader(reader)
                .bookCopy(bookCopy1)
                .build();
        borrowingRepository.save(borrowing1);

        Borrowing borrowing2 = Borrowing.builder()
                .borrowingDate(LocalDate.of(2022, 4, 29))
                .returnDate(LocalDate.of(2022, 8, 13))
                .reader(reader)
                .bookCopy(bookCopy2)
                .build();
        borrowingRepository.save(borrowing2);


        // When
        bookCopy1.setBorrowing(borrowing1);
        bookCopy2.setBorrowing(borrowing2);
        reader.getBorrowings().add(borrowing1);
        reader.getBorrowings().add(borrowing2);

        // Then
        assertEquals(2, reader.getBorrowings().size());
        assertEquals(bookCopy1.getTitle().getTitle(), bookTitle.getTitle());
        assertEquals(bookCopy2.getTitle().getTitle(), bookTitle.getTitle());
        assertTrue(reader.getBorrowings().contains(borrowing1));
        assertTrue(reader.getBorrowings().contains(borrowing2));
        assertNotNull(bookCopy1.getBorrowing());
        assertNotNull(bookCopy2.getBorrowing());
        assertEquals(borrowing1.getId(), bookCopy1.getBorrowing().getId());
        assertEquals(borrowing2.getId(), bookCopy2.getBorrowing().getId());


    }

}
