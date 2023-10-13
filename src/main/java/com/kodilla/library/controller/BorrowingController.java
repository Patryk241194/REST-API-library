package com.kodilla.library.controller;

import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.domain.borrowing.Borrowing;
import com.kodilla.library.dto.BorrowingDto;
import com.kodilla.library.mapper.Mapper;
import com.kodilla.library.service.BorrowingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/borrowings")
public class BorrowingController {

    private final Mapper<BorrowingDto, Borrowing> mapper;
    private final BorrowingService service;

    @GetMapping
    public ResponseEntity<List<BorrowingDto>> getAllBorrowings() {
        log.info("Fetching all BORROWINGS");
        List<Borrowing> borrowings = service.getAllBorrowings();
        return ResponseEntity.ok(mapper.mapToDtoList(borrowings));
    }

    @GetMapping("/{borrowingId}")
    public ResponseEntity<BorrowingDto> getBorrowing(@PathVariable Long borrowingId) {
        log.info("Fetching BORROWING (id={})", borrowingId);
        return ResponseEntity.ok(mapper.mapToDto(service.getBorrowingById(borrowingId)));
    }

    @GetMapping("/reader/{readerId}")
    public ResponseEntity<List<BorrowingDto>> getBorrowingsByReader(@PathVariable Long readerId) {
        log.info("Fetching BORROWINGS for Reader (Reader ID={})", readerId);
        List<Borrowing> borrowings = service.getBorrowingsByReaderId(readerId);
        return ResponseEntity.ok(mapper.mapToDtoList(borrowings));
    }

    @PostMapping(value = "/borrow-book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> borrowBook(@RequestBody BorrowingDto borrowingDto) {
        log.info("Borrowing a book: {}", borrowingDto);
        Borrowing borrowing = mapper.mapToEntity(borrowingDto);
        service.borrowBook(borrowing);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/return-book/{borrowingId}")
    public ResponseEntity<String> returnBook(@PathVariable Long borrowingId, @RequestParam(required = false) CopyStatus status) {
        log.info("Returning a book (Borrowing ID={}, Status={}):", borrowingId, status);
        return ResponseEntity.ok(service.returnBook(borrowingId, status));
    }

    @PatchMapping("/{borrowingId}")
    public ResponseEntity<BorrowingDto> updateBorrowing(@PathVariable Long borrowingId, @RequestBody BorrowingDto borrowingUpdate) {
        log.info("Updating BORROWING (id={})", borrowingId);
        Borrowing updatedBorrowing = service.updateBorrowing(borrowingId, borrowingUpdate);
        return ResponseEntity.ok(mapper.mapToDto(updatedBorrowing));
    }

    @DeleteMapping("/{borrowingId}")
    public ResponseEntity<Void> deleteBorrowing(@PathVariable Long borrowingId) {
        log.info("Deleting BORROWING (id={})", borrowingId);
        service.deleteBorrowingById(borrowingId);
        return ResponseEntity.noContent().build();
    }
}
