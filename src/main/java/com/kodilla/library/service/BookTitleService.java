package com.kodilla.library.service;

import com.kodilla.library.domain.BookTitle;
import com.kodilla.library.error.booktitle.BookTitleNotFoundException;
import com.kodilla.library.repository.BookTitleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookTitleService {

    private final BookTitleRepository bookTitleRepository;

    public List<BookTitle> getAllBookTitles() {
        return bookTitleRepository.findAll();
    }

    public List<BookTitle> getAllBookTitlesByBookCopiesId() {
        return bookTitleRepository.findAll();
    }

    public BookTitle getBookTitleById(final Long bookTitleId) {
        return bookTitleRepository.findById(bookTitleId).orElseThrow(BookTitleNotFoundException::new);
    }

    public BookTitle saveBookTitle(final BookTitle bookTitle) {
        return bookTitleRepository.save(bookTitle);
    }

    public void deleteBookTitle(final Long bookTitleId) {
        if (!bookTitleRepository.existsById(bookTitleId)) {
            throw new BookTitleNotFoundException();
        }
        bookTitleRepository.deleteById(bookTitleId);
    }
}
