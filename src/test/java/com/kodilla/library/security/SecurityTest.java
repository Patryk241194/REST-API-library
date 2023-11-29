package com.kodilla.library.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SecurityTest {

    @Autowired
    private WebClient webClient;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUserAccess() {
        webClient.get()
                .uri("/api/readers")
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAdminAccess() {
        try {
            webClient.post()
                    .uri("/api/readers")
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException e) {
            assertThat(e.getRawStatusCode()).isEqualTo(403);
        }
    }
}