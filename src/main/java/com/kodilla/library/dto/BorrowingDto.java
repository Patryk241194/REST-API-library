package com.kodilla.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class BorrowingDto {
    private Long id;
    private Long bookCopyId;
    private Long readerId;
    private LocalDate borrowingDate;
    private LocalDate returnDate;

}
