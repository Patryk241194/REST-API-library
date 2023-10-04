package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.dto.BookCopyDto;
import com.kodilla.library.service.BookTitleService;
import com.kodilla.library.service.BorrowingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookCopyMapper {

    private final BookTitleService bookTitleService;
    private final BorrowingService borrowingService;

    public BookCopy mapToBookCopy(BookCopyDto bookCopyDto) {

        return new BookCopy(
                bookCopyDto.getId(),
                bookCopyDto.getStatus(),
                (bookCopyDto.getTitleId() != null) ? bookTitleService.getBookTitleById(bookCopyDto.getTitleId()) : null,
                (bookCopyDto.getBorrowingId() != null) ? borrowingService.getBorrowingById(bookCopyDto.getBorrowingId()) : null
        );
    }

    public BookCopyDto mapToBookCopyDto(BookCopy bookCopy) {

        return new BookCopyDto(
                bookCopy.getId(),
                bookCopy.getTitle().getId(),
                bookCopy.getStatus(),
                bookCopy.getBorrowing().getId()
        );
    }
}
