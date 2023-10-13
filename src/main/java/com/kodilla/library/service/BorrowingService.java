package com.kodilla.library.service;

import com.kodilla.library.domain.bookcopy.BookCopy;
import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.domain.borrowing.Borrowing;
import com.kodilla.library.domain.reader.Reader;
import com.kodilla.library.dto.BorrowingDto;
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
        if (!existsById(borrowingId)) {
            throw new BorrowingNotFoundException();
        }
        borrowingRepository.deleteById(borrowingId);
    }

    public boolean existsById(final Long borrowingId) {
        return borrowingRepository.existsById(borrowingId);
    }

    public Borrowing updateBorrowing(Long borrowingId, BorrowingDto updatedBorrowingDto) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(BorrowingNotFoundException::new);

        if (updatedBorrowingDto.getReaderId() != null) {
            borrowing.setReader(readerRepository.findById(updatedBorrowingDto.getReaderId())
                    .orElseThrow(ReaderNotFoundException::new));
        }

        if (updatedBorrowingDto.getBorrowingDate() != null) {
            borrowing.setBorrowingDate(updatedBorrowingDto.getBorrowingDate());
        }

        if (updatedBorrowingDto.getReturnDate() != null) {
            borrowing.setReturnDate(updatedBorrowingDto.getReturnDate());
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
        bookCopyRepository.save(bookCopy);
        borrowingRepository.save(borrowing);

    }

    public String returnBook(Long borrowingId, CopyStatus status) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(BorrowingNotFoundException::new);

        boolean paymentRequired = false;

        if (status == CopyStatus.AVAILABLE) {
            throw new IllegalStateException("The book is available, it should not be borrowed.");
        }

        BookCopy bookCopy = borrowing.getBookCopy();

        if (status == CopyStatus.LOST) {
            status = CopyStatus.AVAILABLE;
            paymentRequired = true;
        } else if (status == null  || status == CopyStatus.BORROWED) {
            status = CopyStatus.AVAILABLE;
        } else if (status == CopyStatus.DAMAGED) {
            status = CopyStatus.DAMAGED;
        }
        bookCopy.setStatus(status);
        borrowingRepository.save(borrowing);
        bookCopyRepository.save(bookCopy);
        deleteBorrowingById(borrowing.getId());

        return buildReturnMessage(status, paymentRequired);
    }

    private String buildReturnMessage(CopyStatus status, boolean paymentRequired) {
        if (status == CopyStatus.DAMAGED) {
            return "The book has been reported as damaged and is currently unavailable. Book requires maintenance.";
        } else if (status == CopyStatus.AVAILABLE && paymentRequired) {
            return "Payment received, the book is now available.";
        } else {
            return "The book has been returned and is available.";
        }
    }
}
