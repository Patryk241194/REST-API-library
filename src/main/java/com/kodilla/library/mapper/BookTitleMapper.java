package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookCopy;
import com.kodilla.library.domain.BookTitle;
import com.kodilla.library.dto.BookTitleDto;
import com.kodilla.library.service.BookCopyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookTitleMapper {

    private final BookCopyService bookCopyService;

    public BookTitle mapToBookTitle(BookTitleDto bookTitleDto) {

        return new BookTitle(
                bookTitleDto.getId(),
                bookTitleDto.getTitle(),
                bookTitleDto.getAuthor(),
                bookTitleDto.getPublicationYear(),
                (bookTitleDto.getCopiesIds() != null) ? bookCopyService.getBookCopiesByBookTitle(bookTitleDto.getId()) : null
        );
    }

    public BookTitleDto mapToBookTitleDto(BookTitle bookTitle) {

        List<Long> copiesIds = bookTitle.getCopies()
                .stream()
                .map(BookCopy::getId)
                .collect(Collectors.toList());

        return new BookTitleDto(
                bookTitle.getId(),
                bookTitle.getTitle(),
                bookTitle.getAuthor(),
                bookTitle.getPublicationYear(),
                copiesIds
        );
    }
}
