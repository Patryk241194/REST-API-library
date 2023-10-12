package com.kodilla.library.mapper;

import com.kodilla.library.domain.borrowing.Borrowing;
import com.kodilla.library.dto.BorrowingDto;
import com.kodilla.library.service.BookCopyService;
import com.kodilla.library.service.ReaderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class BorrowingMapper implements Mapper<BorrowingDto, Borrowing> {

    private final ReaderService readerService;
    private final BookCopyService bookCopyService;

    public Borrowing mapToEntity(BorrowingDto borrowingDto) {

        return new Borrowing(
                borrowingDto.getId(),
                borrowingDto.getBorrowingDate(),
                borrowingDto.getReturnDate(),
                (borrowingDto.getReaderId() != null) ? readerService.getReaderById(borrowingDto.getReaderId()) : null,
                (borrowingDto.getBookCopyId() != null) ? bookCopyService.getBookCopyById(borrowingDto.getBookCopyId()) : null
        );
    }

    public BorrowingDto mapToDto(Borrowing borrowing) {
        Long bookCopyId = (borrowing.getBookCopy() != null) ? borrowing.getBookCopy().getId() : null;
        Long readerId = (borrowing.getReader() != null) ? borrowing.getReader().getId() : null;

        return new BorrowingDto(
                borrowing.getId(),
                bookCopyId,
                readerId,
                borrowing.getBorrowingDate(),
                borrowing.getReturnDate()
        );
    }
}
