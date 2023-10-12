package com.kodilla.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.domain.borrowing.Borrowing;
import com.kodilla.library.dto.BookCopyDto;
import com.kodilla.library.dto.BookTitleDto;
import com.kodilla.library.dto.BorrowingDto;
import com.kodilla.library.dto.ReaderDto;
import com.kodilla.library.service.BookCopyService;
import com.kodilla.library.service.BookTitleService;
import com.kodilla.library.service.BorrowingService;
import com.kodilla.library.service.ReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class BorrowingControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BorrowingService borrowingService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private BookTitleService bookTitleService;
    @Autowired
    private BookCopyService bookCopyService;
    private Borrowing borrowing;
    private BorrowingDto borrowingDto;

    private ReaderDto createSampleReaderDto() {
        return ReaderDto.builder()
                .firstName("John")
                .lastName("Smith")
                .registrationDate(LocalDate.of(2008, 11, 24))
                .build();
    }

    private BookTitleDto createSampleBookTitleDto() {
        return BookTitleDto.builder()
                .title("Sample Title")
                .author("Sample Author")
                .build();
    }

    private BookCopyDto createSampleBookCopyDto(Long bookTitleId) {
        return BookCopyDto.builder()
                .status(CopyStatus.AVAILABLE)
                .publicationYear(2023)
                .titleId(bookTitleId)
                .build();
    }

    private BorrowingDto getSampleBorrowingDto(Long readerId, Long bookCopyId) {
        return BorrowingDto.builder()
                .borrowingDate(LocalDate.of(2023, 9, 29))
                .returnDate(LocalDate.of(2023, 10, 10))
                .readerId(readerId)
                .bookCopyId(bookCopyId)
                .build();
    }

    @BeforeEach
    void setUp() throws Exception {
        ReaderDto readerDto = createSampleReaderDto();
        mockMvc.perform(post("/api/readers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(readerDto)))
                .andExpect(status().isOk());

        BookTitleDto bookTitleDto = createSampleBookTitleDto();
        mockMvc.perform(post("/api/booktitles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookTitleDto)))
                .andExpect(status().isOk());

        Long bookTitleId = bookTitleService.findLatestBookTitleId().getId();
        BookCopyDto bookCopyDto = createSampleBookCopyDto(bookTitleId);
        mockMvc.perform(post("/api/bookcopies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCopyDto)))
                .andExpect(status().isOk());

        Long readerId = readerService.findLatestReaderId().getId();
        Long bookCopyId = bookCopyService.findLatestBookCopyId().getId();
        borrowingDto = getSampleBorrowingDto(readerId,bookCopyId);
        mockMvc.perform(post("/api/borrowings/borrow-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowingDto)))
                .andExpect(status().isOk());

        List<Borrowing> borrowings = borrowingService.getAllBorrowings();
        if (!borrowings.isEmpty()) {
            borrowing = borrowings.get(0);
        }
    }

    @Test
    void testCreateBorrowing() throws Exception {
        mockMvc.perform(get("/api/borrowings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].borrowingDate").isNotEmpty())
                .andExpect(jsonPath("$[0].returnDate").isNotEmpty());
    }

    @Test
    void testGetBorrowingById() throws Exception {
        mockMvc.perform(get("/api/borrowings/{borrowingId}", borrowing.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.borrowingDate").isNotEmpty())
                .andExpect(jsonPath("$.returnDate").isNotEmpty());
    }

    @Test
    void testUpdateBorrowing() throws Exception {
        borrowing.setReturnDate(borrowing.getReturnDate().plusDays(3));

        mockMvc.perform(patch("/api/borrowings/{borrowingId}", borrowing.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowing)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/borrowings/{borrowingId}", borrowing.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.borrowingDate").isNotEmpty())
                .andExpect(jsonPath("$.returnDate").isNotEmpty())
                .andExpect(jsonPath("$.returnDate", endsWith("2023-10-13")));
    }

    @Test
    void testDeleteBorrowing() throws Exception {
        mockMvc.perform(delete("/api/borrowings/{borrowingId}", borrowing.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/borrowings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
