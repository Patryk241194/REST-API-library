package com.kodilla.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class BookTitleDto {
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
    private List<Long> copiesIds;

}
