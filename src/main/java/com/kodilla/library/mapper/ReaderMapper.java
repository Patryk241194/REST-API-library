package com.kodilla.library.mapper;

import com.kodilla.library.domain.borrowing.Borrowing;
import com.kodilla.library.domain.reader.Reader;
import com.kodilla.library.dto.ReaderDto;
import com.kodilla.library.service.BorrowingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReaderMapper implements Mapper<ReaderDto, Reader> {

    private final BorrowingService borrowingService;

    public Reader mapToEntity(final ReaderDto readerDto) {

        return new Reader(
                readerDto.getId(),
                readerDto.getFirstName(),
                readerDto.getLastName(),
                readerDto.getRegistrationDate(),
                (readerDto.getBorrowingIds() != null) ? borrowingService.getBorrowingsByReaderId(readerDto.getId()) : null
        );
    }

    public ReaderDto mapToDto(final Reader reader) {
        List<Long> borrowingIds = (reader.getBorrowings() != null)
                ? reader.getBorrowings().stream().map(Borrowing::getId).collect(Collectors.toList())
                : null;

        return new ReaderDto(
                reader.getId(),
                reader.getFirstName(),
                reader.getLastName(),
                reader.getRegistrationDate(),
                borrowingIds
        );
    }
}
