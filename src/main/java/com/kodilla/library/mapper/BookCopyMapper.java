package com.kodilla.library.mapper;

import com.kodilla.library.domain.bookcopy.BookCopy;
import com.kodilla.library.domain.booktitle.BookTitle;
import com.kodilla.library.domain.borrowing.Borrowing;
import com.kodilla.library.dto.BookCopyDto;
import com.kodilla.library.service.BookTitleService;
import com.kodilla.library.service.BorrowingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookCopyMapper implements Mapper<BookCopyDto, BookCopy> {

    private final BookTitleService bookTitleService;
    private final BorrowingService borrowingService;

    public BookCopy mapToEntity(BookCopyDto bookCopyDto) {

        return new BookCopy(
                bookCopyDto.getId(),
                bookCopyDto.getStatus(),
                bookCopyDto.getPublicationYear(),
                (bookCopyDto.getTitleId() != null) ? bookTitleService.getBookTitleById(bookCopyDto.getTitleId()) : null,
                (bookCopyDto.getBorrowingId() != null) ? borrowingService.getBorrowingById(bookCopyDto.getBorrowingId()) : null
        );
    }

    public BookCopyDto mapToDto(BookCopy bookCopy) {
        Long titleId = (bookCopy.getTitle() != null) ? bookCopy.getTitle().getId() : null;
        Long borrowingId = (bookCopy.getBorrowing() != null) ? bookCopy.getBorrowing().getId() : null;

        return new BookCopyDto(
                bookCopy.getId(),
                titleId,
                bookCopy.getStatus(),
                bookCopy.getPublicationYear(),
                borrowingId
        );
    }
}
