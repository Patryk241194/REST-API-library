package com.kodilla.library.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kodilla.library.domain.bookcopy.BookCopy;
import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.domain.booktitle.BookTitle;
import com.kodilla.library.error.bookcopy.BookCopyNotFoundException;
import com.kodilla.library.error.booktitle.BookTitleNotFoundException;
import com.kodilla.library.error.borrowing.BorrowingNotFoundException;
import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BookTitleRepository;
import com.kodilla.library.repository.BorrowingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    }

    public void deleteBookCopyById(final Long bookCopyId) {
        if (!bookCopyRepository.existsById(bookCopyId)) {
            throw new BookCopyNotFoundException();
        }
        bookCopyRepository.deleteById(bookCopyId);
    }

    public BookCopy updateBookCopy(Long bookCopyId, JsonNode updates) {
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(BookCopyNotFoundException::new);

        if (updates.has("titleId")) {
            bookCopy.setTitle(bookTitleRepository.findById(updates.get("titleId").asLong())
                    .orElseThrow(BookTitleNotFoundException::new));
        }

        if (updates.has("status")) {
            bookCopy.setStatus(CopyStatus.valueOf(updates.get("status").asText()));
        }

        if (updates.has("publicationYear")) {
            bookCopy.setPublicationYear(updates.get("publicationYear").asInt());
        }

        if (updates.has("borrowingId")) {
            bookCopy.setBorrowing(borrowingRepository.findById(updates.get("borrowingId").asLong())
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
}
