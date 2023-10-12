package com.kodilla.library.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kodilla.library.domain.bookcopy.BookCopy;
import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.domain.booktitle.BookTitle;
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

    public Long getAvailableCopiesCount(Long bookTitleId) {
        BookTitle bookTitle = getBookTitleById(bookTitleId);
        List<BookCopy> copies = bookTitle.getCopies();
        return copies.stream()
                .filter(copy -> CopyStatus.AVAILABLE.equals(copy.getStatus()))
                .count();
    }

    public BookTitle saveBookTitle(final BookTitle bookTitle) {
        return bookTitleRepository.save(bookTitle);
    }

    public void deleteBookTitleById(final Long bookTitleId) {
        if (!bookTitleRepository.existsById(bookTitleId)) {
            throw new BookTitleNotFoundException();
        }
        bookTitleRepository.deleteById(bookTitleId);
    }

    public BookTitle updateBookTitle(Long bookTitleId, JsonNode updates) {
        BookTitle bookTitle = bookTitleRepository.findById(bookTitleId)
                .orElseThrow(BookTitleNotFoundException::new);

        if (updates.has("title")) {
            bookTitle.setTitle(updates.get("title").asText());
        }

        if (updates.has("author")) {
            bookTitle.setAuthor(updates.get("author").asText());
        }

        return bookTitleRepository.save(bookTitle);
    }

    public BookTitle findLatestBookTitleId() {
        return bookTitleRepository.findFirstByOrderByIdDesc().orElseThrow(BookTitleNotFoundException::new);
    }
}
