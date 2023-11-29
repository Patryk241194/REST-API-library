package com.kodilla.library.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.domain.reader.Reader;
import com.kodilla.library.dto.ReaderDto;
import com.kodilla.library.mapper.Mapper;
import com.kodilla.library.service.ReaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/readers")
public class ReaderController {

    private final Mapper<ReaderDto, Reader> mapper;
    private final ReaderService service;

    @GetMapping
    public ResponseEntity<List<ReaderDto>> getAllReaders() {
        log.info("Fetching all READERS");
        List<Reader> readers = service.getAllReaders();
        return ResponseEntity.ok(mapper.mapToDtoList(readers));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{readerId}")
    public ResponseEntity<ReaderDto> getReader(@PathVariable Long readerId) {
        log.info("Fetching READER(id={})", readerId);
        return ResponseEntity.ok(mapper.mapToDto(service.getReaderById(readerId)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/with-borrowings")
    public ResponseEntity<List<ReaderDto>> getReadersWithBorrowings(@RequestParam CopyStatus status) {
        log.info("Fetching readers with borrowings of status: {}", status);
        List<Reader> readers = service.getReadersWithBorrowings(status);
        return ResponseEntity.ok(mapper.mapToDtoList(readers));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/overdue-readers")
    public ResponseEntity<List<ReaderDto>> getOverdueReaders() {
        log.info("Fetching readers with overdue borrowings");
        List<Reader> readers = service.getReadersWithOverdueBorrowings();
        return ResponseEntity.ok(mapper.mapToDtoList(readers));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createReader(@RequestBody ReaderDto readerDto) {
        log.info("Creating a new READER: {}", readerDto);
        Reader reader = mapper.mapToEntity(readerDto);
        service.saveReader(reader);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{readerId}")
    public ResponseEntity<ReaderDto> updateReader(@PathVariable Long readerId, @RequestBody JsonNode readerUpdate) {
        log.info("Updating READER(id={})", readerId);
        Reader updatedReader = service.updateReader(readerId, readerUpdate);
        return ResponseEntity.ok(mapper.mapToDto(updatedReader));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{readerId}")
    public ResponseEntity<Void> deleteReader(@PathVariable Long readerId) {
        log.info("Deleting READER(id={})", readerId);
        service.deleteReaderById(readerId);
        return ResponseEntity.noContent().build();
    }
}
