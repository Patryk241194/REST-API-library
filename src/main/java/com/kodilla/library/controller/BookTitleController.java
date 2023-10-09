package com.kodilla.library.controller;

import com.fasterxml.jackson.databind.JsonNode;

import com.kodilla.library.domain.booktitle.BookTitle;
import com.kodilla.library.dto.BookTitleDto;
import com.kodilla.library.mapper.Mapper;
import com.kodilla.library.service.BookTitleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booktitles")
public class BookTitleController {

    private final Mapper<BookTitleDto, BookTitle> mapper;
    private final BookTitleService service;

    @GetMapping
    public ResponseEntity<List<BookTitleDto>> getAllBookTitles() {
        log.info("Fetching all BOOK TITLES");
        List<BookTitle> bookTitles = service.getAllBookTitles();
        return ResponseEntity.ok(mapper.mapToDtoList(bookTitles));
    }

    @GetMapping("/{bookTitleId}")
    public ResponseEntity<BookTitleDto> getBookTitle(@PathVariable Long bookTitleId) {
        log.info("Fetching BOOK TITLE (id={})", bookTitleId);
        return ResponseEntity.ok(mapper.mapToDto(service.getBookTitleById(bookTitleId)));
    }

    @GetMapping("/{bookTitleId}/available-copies-count")
    public ResponseEntity<Long> getAvailableCopiesCount(@PathVariable Long bookTitleId) {
        log.info("Fetching available copies count for BOOK TITLE (id={})", bookTitleId);
        Long count = service.getAvailableCopiesCount(bookTitleId);
        return ResponseEntity.ok(count);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBookTitle(@RequestBody BookTitleDto bookTitleDto) {
        log.info("Creating a new BOOK TITLE: {}", bookTitleDto);
        BookTitle bookTitle = mapper.mapToEntity(bookTitleDto);
        service.saveBookTitle(bookTitle);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{bookTitleId}")
    public ResponseEntity<BookTitleDto> updateBookTitle(@PathVariable Long bookTitleId, @RequestBody JsonNode bookTitleUpdate) {
        log.info("Updating BOOK TITLE (id={})", bookTitleId);
        BookTitle updatedBookTitle = service.updateBookTitle(bookTitleId, bookTitleUpdate);
        return ResponseEntity.ok(mapper.mapToDto(updatedBookTitle));
    }

    @DeleteMapping("/{bookTitleId}")
    public ResponseEntity<Void> deleteBookTitle(@PathVariable Long bookTitleId) {
        log.info("Deleting BOOK TITLE (id={})", bookTitleId);
        service.deleteBookTitleById(bookTitleId);
        return ResponseEntity.noContent().build();
    }
}
