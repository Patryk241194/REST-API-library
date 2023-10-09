package com.kodilla.library.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kodilla.library.domain.bookcopy.BookCopy;
import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.domain.borrowing.Borrowing;
import com.kodilla.library.domain.reader.Reader;
import com.kodilla.library.error.bookcopy.BookCopyNotFoundException;
import com.kodilla.library.error.borrowing.BorrowingNotFoundException;
import com.kodilla.library.error.reader.ReaderNotFoundException;
import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BorrowingRepository;
import com.kodilla.library.repository.ReaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BookCopyRepository bookCopyRepository;
    private final ReaderRepository readerRepository;

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    public List<Borrowing> getBorrowingsByReaderId(final Long readerId) {
        return borrowingRepository.findAllByReader_Id(readerId);
    }

    public Borrowing getBorrowingById(final Long borrowingId) {
        return borrowingRepository.findById(borrowingId).orElseThrow(BorrowingNotFoundException::new);
    }

    public void deleteBorrowingById(final Long borrowingId) {
        if (!borrowingRepository.existsById(borrowingId)) {
            throw new BorrowingNotFoundException();
        }
        borrowingRepository.deleteById(borrowingId);
    }

    public Borrowing updateBorrowing(Long borrowingId, JsonNode updates) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(BorrowingNotFoundException::new);

        if (updates.has("bookCopyId")) {
            borrowing.setBookCopy(bookCopyRepository.findById(updates.get("bookCopyId").asLong())
                    .orElseThrow(BookCopyNotFoundException::new));
        }

        if (updates.has("readerId")) {
            borrowing.setReader(readerRepository.findById(updates.get("readerId").asLong())
                    .orElseThrow(ReaderNotFoundException::new));
        }

        if (updates.has("borrowingDate")) {
            borrowing.setBorrowingDate(LocalDate.parse(updates.get("borrowingDate").asText()));
        }

        if (updates.has("returnDate")) {
            borrowing.setReturnDate(LocalDate.parse(updates.get("returnDate").asText()));
        }

        return borrowingRepository.save(borrowing);
    }

    public void borrowBook(Borrowing borrowing) {
        BookCopy bookCopy = borrowing.getBookCopy();
        Reader reader = borrowing.getReader();

        if (bookCopy == null || reader == null) {
            throw new IllegalArgumentException("Both bookCopy and reader must be provided to borrow a book.");
        }

        if (bookCopy.getStatus() != CopyStatus.AVAILABLE) {
            throw new IllegalStateException("The book copy is not available for borrowing.");
        }

        borrowing.setBorrowingDate(LocalDate.now());
        bookCopy.setStatus(CopyStatus.BORROWED);
        borrowingRepository.save(borrowing);
        bookCopyRepository.save(bookCopy);
    }

    public String returnBook(Long borrowingId, CopyStatus status) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(BorrowingNotFoundException::new);

        if (borrowing.getReturnDate() != null) {
            throw new IllegalStateException("The book has already been returned.");
        }

        BookCopy bookCopy = borrowing.getBookCopy();
        borrowing.setReturnDate(LocalDate.now());

        if (status == null || status == CopyStatus.LOST) {
            status = CopyStatus.AVAILABLE;
        } else if (status == CopyStatus.DAMAGED) {
            status = CopyStatus.DAMAGED;
        }
        bookCopy.setStatus(status);
        borrowingRepository.save(borrowing);
        bookCopyRepository.save(bookCopy);

        return buildReturnMessage(status);
    }

    private String buildReturnMessage(CopyStatus status) {
        if (status == CopyStatus.LOST) {
            return "Payment received, the book is now available.";
        } else if (status == CopyStatus.DAMAGED) {
            return "The book has been reported as damaged and is currently unavailable. Book requires maintenance.";
        } else {
            return "The book has been returned and is available.";
        }
    }
}
