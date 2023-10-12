package com.kodilla.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.library.domain.booktitle.BookTitle;
import com.kodilla.library.dto.BookTitleDto;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class BookTitleControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookTitleService bookTitleService;
    private BookTitle bookTitle;
    private BookTitleDto bookTitleDto;

    private BookTitleDto createSampleBookTitleDto() {
        return BookTitleDto.builder()
                .title("Sample Title")
                .author("Sample Author")
                .build();
    }

    @BeforeEach
    void setUp() throws Exception {
        bookTitleDto = createSampleBookTitleDto();
        mockMvc.perform(post("/api/booktitles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookTitleDto)))
                .andExpect(status().isOk());

        List<BookTitle> bookTitles = bookTitleService.getAllBookTitles();
        if (!bookTitles.isEmpty()) {
            bookTitle = bookTitles.get(0);
        }
    }

    @Test
    void testCreateBookTitle() throws Exception {
        mockMvc.perform(get("/api/booktitles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Sample Title")))
                .andExpect(jsonPath("$[0].author", is("Sample Author")));
    }

    @Test
    void testGetBookTitleById() throws Exception {
        mockMvc.perform(get("/api/booktitles/{bookTitleId}", bookTitle.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Sample Title")))
                .andExpect(jsonPath("$.author", is("Sample Author")));
    }

    @Test
    void testUpdateBookTitle() throws Exception {
        bookTitle.setTitle("Updated Title");
        bookTitle.setAuthor("Updated Author");

        mockMvc.perform(patch("/api/booktitles/{bookTitleId}", bookTitle.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookTitle)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/booktitles/{bookTitleId}", bookTitle.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.author", is("Updated Author")));
    }

    @Test
    void testDeleteBookTitle() throws Exception {
        mockMvc.perform(delete("/api/booktitles/{bookTitleId}", bookTitle.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/booktitles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
