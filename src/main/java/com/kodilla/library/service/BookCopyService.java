package com.kodilla.library.service;

import com.kodilla.library.domain.bookcopy.BookCopy;
import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.domain.booktitle.BookTitle;
import com.kodilla.library.dto.BookCopyDto;
import com.kodilla.library.error.bookcopy.BookCopyNotFoundException;
import com.kodilla.library.error.booktitle.BookTitleNotFoundException;
import com.kodilla.library.error.borrowing.BorrowingNotFoundException;
import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BookTitleRepository;
import com.kodilla.library.repository.BorrowingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookTitleRepository bookTitleRepository;
    private final BorrowingRepository borrowingRepository;

    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepository.findAll();
    }

    public List<BookCopy> getBookCopiesByBookTitle(final Long bookTitleId) {
        return bookCopyRepository.findAllByTitle_Id(bookTitleId);
    }

    public BookCopy getBookCopyByBorrowingId(final Long borrowingId) {
        return bookCopyRepository.findBookCopyByBorrowingId(borrowingId);
    }

    public BookCopy getBookCopyById(final Long bookCopyId) {
        return bookCopyRepository.findById(bookCopyId).orElseThrow(BookCopyNotFoundException::new);
    }

    public void saveBookCopy(BookCopy bookCopy) {
        bookCopyRepository.save(bookCopy);

        BookTitle bookTitle = bookTitleRepository.findById(bookCopy.getTitle().getId())
                .orElseThrow(BookTitleNotFoundException::new);

        if (bookTitle.getCopies() == null) {
            bookTitle.setCopies(new ArrayList<>());
        }

        bookTitle.getCopies().add(bookCopy);
        bookTitleRepository.save(bookTitle);
    }


    public void deleteBookCopyById(final Long bookCopyId) {
        if (!bookCopyRepository.existsById(bookCopyId)) {
            throw new BookCopyNotFoundException();
        }
        bookCopyRepository.deleteById(bookCopyId);
    }

    public BookCopy updateBookCopy(Long bookCopyId, BookCopyDto updatedBookCopyDto) {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(BookCopyNotFoundException::new);

        if (updatedBookCopyDto.getStatus() != null) {
            bookCopy.setStatus(updatedBookCopyDto.getStatus());
        }

        if (updatedBookCopyDto.getPublicationYear() != null) {
            bookCopy.setPublicationYear(updatedBookCopyDto.getPublicationYear());
        }

        if (updatedBookCopyDto.getBorrowingId() != null) {
            bookCopy.setBorrowing(borrowingRepository.findById(updatedBookCopyDto.getBorrowingId())
                    .orElseThrow(BorrowingNotFoundException::new));
        }

        return bookCopyRepository.save(bookCopy);
    }

    public void changeCopyStatus(Long copyId, CopyStatus status) {
        BookCopy bookCopy = bookCopyRepository.findById(copyId)
                .orElseThrow(BookCopyNotFoundException::new);
        bookCopy.setStatus(status);
        bookCopyRepository.save(bookCopy);
    }

    public BookCopy findLatestBookCopyId() {
        return bookCopyRepository.findFirstByOrderByIdDesc()
                .orElseThrow(BookCopyNotFoundException::new);
    }
}
