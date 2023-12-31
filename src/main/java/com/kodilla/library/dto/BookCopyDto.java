package com.kodilla.library.dto;

import com.kodilla.library.domain.bookcopy.CopyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BookCopyDto {
    private Long id;
    private Long titleId;
    private CopyStatus status;
    private Integer publicationYear;
    private Long borrowingId;

}
