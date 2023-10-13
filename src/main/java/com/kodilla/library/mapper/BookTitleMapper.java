package com.kodilla.library.mapper;

import com.kodilla.library.domain.bookcopy.BookCopy;
import com.kodilla.library.domain.booktitle.BookTitle;
import com.kodilla.library.dto.BookTitleDto;
import com.kodilla.library.service.BookCopyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookTitleMapper implements Mapper<BookTitleDto, BookTitle> {

    private final BookCopyService bookCopyService;

    public BookTitle mapToEntity(final BookTitleDto bookTitleDto) {

        return new BookTitle(
                bookTitleDto.getId(),
                bookTitleDto.getTitle(),
                bookTitleDto.getAuthor(),
                (bookTitleDto.getCopiesIds() != null) ? bookCopyService.getBookCopiesByBookTitle(bookTitleDto.getId()) : null
        );
    }

    public BookTitleDto mapToDto(final BookTitle bookTitle) {
        List<Long> copiesIds = (bookTitle.getCopies() != null)
                ? bookTitle.getCopies().stream().map(BookCopy::getId).collect(Collectors.toList())
                : null;

        return new BookTitleDto(
                bookTitle.getId(),
                bookTitle.getTitle(),
                bookTitle.getAuthor(),
                copiesIds
        );
    }
}
