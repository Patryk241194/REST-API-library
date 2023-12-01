package com.kodilla.library.security;

import com.kodilla.library.dto.ReaderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void shouldReturnOkForUserOnGetReadersEndpoint() {
        webTestClient.get()
                .uri("/api/readers")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void shouldReturnOkForAdminOnGetReadersEndpoint() {
        webTestClient.get()
                .uri("/api/readers")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void shouldReturnOkForAdminOnPostReaderEndpoint() {
        ReaderDto readerDto = ReaderDto.builder()
                .firstName("John")
                .lastName("Smith")
                .registrationDate(LocalDate.of(2023, 9, 29))
                .build();

        webTestClient.post()
                .uri("/api/readers")
                .bodyValue(readerDto)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void shouldReturnForbiddenForUserOnPostReaderEndpoint() {
        ReaderDto readerDto = ReaderDto.builder()
                .firstName("John")
                .lastName("Smith")
                .registrationDate(LocalDate.of(2023, 9, 29))
                .build();

        webTestClient.post()
                .uri("/api/readers")
                .bodyValue(readerDto)
                .exchange()
                .expectStatus().isForbidden();
    }

}