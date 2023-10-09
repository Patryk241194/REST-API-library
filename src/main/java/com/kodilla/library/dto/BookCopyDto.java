package com.kodilla.library.dto;

import com.kodilla.library.domain.bookcopy.CopyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder
public class BookCopyDto {
    private Long id;
    private Long titleId;
    private CopyStatus status;
    private int publicationYear;
    private Long borrowingId;

}
