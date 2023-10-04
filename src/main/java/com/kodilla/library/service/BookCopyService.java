package com.kodilla.library.service;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookTitle;
import com.kodilla.library.domain.Borrowing;
import com.kodilla.library.error.bookcopy.BookCopyNotFoundException;
import com.kodilla.library.error.booktitle.BookTitleNotFoundException;
import com.kodilla.library.repository.BookCopyRepository;
import com.kodilla.library.repository.BookTitleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookCopyService {

    private final BookCopyRepository bookCopyRepository;

    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepository.findAll();
    }

    public List<BookCopy> getBookCopiesByBookTitle(final Long bookTitleId) {
/*        BookTitle bookTitle = bookTitleRepository.findById(bookTitleId).
                orElseThrow(BookTitleNotFoundException::new);

        return bookCopyRepository.findAll()
                .stream()
                .filter(bookCopy -> bookCopy.getTitle().equals(bookTitle))
                .collect(Collectors.toList());*/
        return bookCopyRepository.findAllByTitle_Id(bookTitleId);
    }

    public BookCopy getBookCopyByBorrowingId(final Long borrowingId) {
        return bookCopyRepository.findBookCopyByBorrowingId(borrowingId);
    }

    public BookCopy getBookCopyById(final Long bookCopyId) {
        return bookCopyRepository.findById(bookCopyId).orElseThrow(BookCopyNotFoundException::new);
    }

    public BookCopy saveBookCopy(final BookCopy bookCopy) {
        return bookCopyRepository.save(bookCopy);
    }

    public void deleteBookCopy(final Long bookCopyId) {
        if (!bookCopyRepository.existsById(bookCopyId)) {
            throw new BookCopyNotFoundException();
        }
        bookCopyRepository.deleteById(bookCopyId);
    }
}
