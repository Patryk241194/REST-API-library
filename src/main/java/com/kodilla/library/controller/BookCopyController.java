package com.kodilla.library.controller;

import com.fasterxml.jackson.databind.JsonNode;

import com.kodilla.library.domain.bookcopy.BookCopy;
import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.domain.booktitle.BookTitle;
import com.kodilla.library.dto.BookCopyDto;
import com.kodilla.library.mapper.Mapper;
import com.kodilla.library.service.BookCopyService;
import com.kodilla.library.service.BookTitleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookcopies")
public class BookCopyController {

    private final Mapper<BookCopyDto, BookCopy> mapper;
    private final BookCopyService service;
    private final BookTitleService bookTitleService;

    @GetMapping
    public ResponseEntity<List<BookCopyDto>> getAllBookCopies() {
        log.info("Fetching all BOOK COPIES");
        List<BookCopy> bookCopies = service.getAllBookCopies();
        return ResponseEntity.ok(mapper.mapToDtoList(bookCopies));
    }

    @GetMapping("/{bookCopyId}")
    public ResponseEntity<BookCopyDto> getBookCopy(@PathVariable Long bookCopyId) {
        log.info("Fetching BOOK COPY (id={})", bookCopyId);
        return ResponseEntity.ok(mapper.mapToDto(service.getBookCopyById(bookCopyId)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBookCopy(@Valid @RequestBody BookCopyDto bookCopyDto) {
        log.info("Creating a new BOOK COPY: {}", bookCopyDto);
        BookCopy bookCopy = mapper.mapToEntity(bookCopyDto);
        service.saveBookCopy(bookCopy);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{bookCopyId}")
    public ResponseEntity<BookCopyDto> updateBookCopy(@PathVariable Long bookCopyId, @RequestBody JsonNode bookCopyUpdate) {
        log.info("Updating BOOK COPY (id={})", bookCopyId);
        BookCopy updatedBookCopy = service.updateBookCopy(bookCopyId, bookCopyUpdate);
        return ResponseEntity.ok(mapper.mapToDto(updatedBookCopy));
    }

    @PatchMapping("/{bookCopyId}/change-status")
    public ResponseEntity<Void> changeCopyStatus(@PathVariable Long bookCopyId, @RequestParam @Valid CopyStatus status) {
        log.info("Changing BOOK COPY status (id={})", bookCopyId);
        service.changeCopyStatus(bookCopyId, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookCopyId}")
    public ResponseEntity<Void> deleteBookCopy(@PathVariable Long bookCopyId) {
        log.info("Deleting BOOK COPY (id={})", bookCopyId);
        service.deleteBookCopyById(bookCopyId);
        return ResponseEntity.noContent().build();
    }
}
