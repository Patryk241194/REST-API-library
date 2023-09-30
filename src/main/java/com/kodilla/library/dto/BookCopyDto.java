package com.kodilla.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BookCopyDto {
    private Long id;
    private Long titleId;
    private String status;

}
