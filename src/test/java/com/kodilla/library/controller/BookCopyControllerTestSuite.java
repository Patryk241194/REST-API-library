package com.kodilla.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.library.domain.bookcopy.BookCopy;
import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.dto.BookCopyDto;
import com.kodilla.library.dto.BookTitleDto;
import com.kodilla.library.service.BookCopyService;
import com.kodilla.library.service.BookTitleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class BookCopyControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookCopyService bookCopyService;
    @Autowired
    private BookTitleService bookTitleService;
    private BookCopy bookCopy;
    private BookCopyDto bookCopyDto;

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

    @BeforeEach
    void setUp() throws Exception {
        BookTitleDto bookTitleDto = createSampleBookTitleDto();
        mockMvc.perform(post("/api/booktitles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookTitleDto)))
                .andExpect(status().isOk());


        Long id = bookTitleService.findLatestBookTitleId().getId();
        bookCopyDto = createSampleBookCopyDto(id);
        mockMvc.perform(post("/api/bookcopies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCopyDto)))
                .andExpect(status().isOk());

        List<BookCopy> bookCopies = bookCopyService.getAllBookCopies();
        if (!bookCopies.isEmpty()) {
            bookCopy = bookCopies.get(0);
        }
    }

    @Test
    public void testCreateBookCopy() throws Exception {
        mockMvc.perform(get("/api/bookcopies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", is("AVAILABLE")))
                .andExpect(jsonPath("$[0].publicationYear", is(2023)));
    }

    @Test
    void testGetBookCopyById() throws Exception {
        mockMvc.perform(get("/api/bookcopies/{bookCopyId}", bookCopy.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("AVAILABLE")))
                .andExpect(jsonPath("$.publicationYear", is(2023)));
    }

    @Test
    void testUpdateBookCopy() throws Exception {
        bookCopy.setStatus(CopyStatus.BORROWED);
        bookCopy.setPublicationYear(2022);

        mockMvc.perform(patch("/api/bookcopies/{bookCopyId}", bookCopy.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCopy)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/bookcopies/{bookCopyId}", bookCopy.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("BORROWED")))
                .andExpect(jsonPath("$.publicationYear", is(2022)));
    }

    @Test
    void testDeleteBookCopy() throws Exception {
        mockMvc.perform(delete("/api/bookcopies/{bookCopyId}", bookCopy.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/bookcopies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
