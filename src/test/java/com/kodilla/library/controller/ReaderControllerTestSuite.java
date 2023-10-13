package com.kodilla.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.library.domain.reader.Reader;
import com.kodilla.library.dto.ReaderDto;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ReaderControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ReaderService readerService;
    private Reader reader;
    private ReaderDto readerDto;

    private ReaderDto createSampleReaderDto() {
        return ReaderDto.builder()
                .firstName("John")
                .lastName("Smith")
                .registrationDate(LocalDate.now())
                .build();
    }

    @BeforeEach
    void setUp() throws Exception {
        readerDto = createSampleReaderDto();
        mockMvc.perform(post("/api/readers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(readerDto)))
                .andExpect(status().isOk());

        List<Reader> readers = readerService.getAllReaders();
        if (!readers.isEmpty()) {
            reader = readers.get(0);
        }
    }

    @Test
    void testCreateReader() throws Exception {
        mockMvc.perform(get("/api/readers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Smith")));
    }

    @Test
    void testGetReaderById() throws Exception {
        mockMvc.perform(get("/api/readers/{readerId}", reader.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Smith")));
    }

    @Test
    void testUpdateReader() throws Exception {
        reader.setFirstName("Patryk");
        reader.setLastName("Potrykus");

        mockMvc.perform(patch("/api/readers/{readerId}", reader.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reader)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/readers/{readerId}", reader.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Patryk")))
                .andExpect(jsonPath("$.lastName", is("Potrykus")));
    }

    @Test
    void testDeleteReader() throws Exception {
        mockMvc.perform(delete("/api/readers/{readerId}", reader.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/readers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
