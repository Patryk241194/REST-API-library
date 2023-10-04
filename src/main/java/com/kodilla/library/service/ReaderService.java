package com.kodilla.library.service;

import com.kodilla.library.domain.Borrowing;
import com.kodilla.library.domain.Reader;
import com.kodilla.library.error.borrowing.BorrowingNotFoundException;
import com.kodilla.library.error.reader.ReaderNotFoundException;
import com.kodilla.library.repository.BorrowingRepository;
import com.kodilla.library.repository.ReaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;

    private final BorrowingRepository borrowingRepository;

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public Reader getReaderById(final Long readerId) {
        return readerRepository.findById(readerId).orElseThrow(ReaderNotFoundException::new);
    }

    public Reader getReaderByBorrowingId(final Long borrowingId) {

        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(BorrowingNotFoundException::new);

        return readerRepository.findReaderByBorrowingsContains(borrowing);
    }

    public Reader saveReader(final Reader reader) {
        return readerRepository.save(reader);
    }

    public void deleteReader(final Long readerId) {
        if (!readerRepository.existsById(readerId)) {
            throw new ReaderNotFoundException();
        }
        readerRepository.deleteById(readerId);
    }


}
