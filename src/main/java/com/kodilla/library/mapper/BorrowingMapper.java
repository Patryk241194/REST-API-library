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
                (borrowingDto.getReaderId() != null) ? readerService.getReaderByBorrowingId(borrowingDto.getId()) : null,
                (borrowingDto.getBookCopyId() != null) ? bookCopyService.getBookCopyByBorrowingId(borrowingDto.getId()) : null
        );
    }

    public BorrowingDto mapToDto(Borrowing borrowing) {

        return new BorrowingDto(
                borrowing.getId(),
                borrowing.getBookCopy().getId(),
                borrowing.getReader().getId(),
                borrowing.getBorrowingDate(),
                borrowing.getReturnDate()
        );
    }
}
