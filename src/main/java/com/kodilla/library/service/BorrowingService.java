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
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    public List<Borrowing> getBorrowingsByReaderId(final Long readerId) {
/*        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(ReaderNotFoundException::new);

        return borrowingRepository.findAll()
                .stream()
                .filter(borrowing -> borrowing.getReader().equals(reader))
                .collect(Collectors.toList());*/

        return borrowingRepository.findAllByReader_Id(readerId);
    }

    public Borrowing getBorrowingById(final Long borrowingId) {
        return borrowingRepository.findById(borrowingId).orElseThrow(BorrowingNotFoundException::new);
    }

    public Borrowing saveBorrowing(final Borrowing borrowing) {
        return borrowingRepository.save(borrowing);
    }

    public void deleteBorrowing(final Long borrowingId) {
        if (!borrowingRepository.existsById(borrowingId)) {
            throw new BorrowingNotFoundException();
        }
        borrowingRepository.deleteById(borrowingId);
    }
}
