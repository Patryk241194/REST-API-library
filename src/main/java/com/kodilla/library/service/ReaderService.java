package com.kodilla.library.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.domain.borrowing.Borrowing;
import com.kodilla.library.domain.reader.Reader;
import com.kodilla.library.error.borrowing.BorrowingNotFoundException;
import com.kodilla.library.error.reader.ReaderNotFoundException;
import com.kodilla.library.repository.BorrowingRepository;
import com.kodilla.library.repository.ReaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public List<Reader> getReadersWithBorrowings(CopyStatus status) {
        List<Reader> readers = new ArrayList<>();
        List<Borrowing> borrowings = borrowingRepository.findByBookCopy_Status(status);

        for (Borrowing borrowing : borrowings) {
            readers.add(borrowing.getReader());
        }

        return readers;
    }

    public List<Reader> getReadersWithOverdueBorrowings() {
        LocalDate currentDate = LocalDate.now();
        List<Reader> readers = new ArrayList<>();
        List<Borrowing> borrowings = borrowingRepository.findByReturnDateBeforeAndBookCopy_Status(currentDate, CopyStatus.BORROWED);

        for (Borrowing borrowing : borrowings) {
            readers.add(borrowing.getReader());
        }

        return readers;
    }

    public Reader saveReader(final Reader reader) {
        return readerRepository.save(reader);
    }

    public void deleteReaderById(final Long readerId) {
        if (!readerRepository.existsById(readerId)) {
            throw new ReaderNotFoundException();
        }
        readerRepository.deleteById(readerId);
    }

    public Reader updateReader(Long readerId, JsonNode updates) {
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(ReaderNotFoundException::new);

        if (updates.has("firstName")) {
            reader.setFirstName(updates.get("firstName").asText());
        }

        if (updates.has("lastName")) {
            reader.setLastName(updates.get("lastName").asText());
        }

        if (updates.has("registrationDate")) {
            reader.setRegistrationDate(LocalDate.parse(updates.get("registrationDate").asText()));
        }

        return readerRepository.save(reader);
    }
}
