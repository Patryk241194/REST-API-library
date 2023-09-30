package com.kodilla.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class ReaderDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate registrationDate;

}
