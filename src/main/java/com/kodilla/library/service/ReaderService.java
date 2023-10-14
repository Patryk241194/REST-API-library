package com.kodilla.library.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.domain.borrowing.Borrowing;
import com.kodilla.library.domain.reader.Reader;
import com.kodilla.library.error.reader.ReaderNotFoundException;
import com.kodilla.library.repository.BorrowingRepository;
import com.kodilla.library.repository.ReaderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        Map<Reader, List<Long>> overdueBorrowings = new HashMap<>();

        List<Borrowing> borrowings = borrowingRepository.findByReturnDateBeforeAndBookCopy_Status(currentDate, CopyStatus.BORROWED);

        for (Borrowing borrowing : borrowings) {
            Reader reader = borrowing.getReader();
            if (reader != null) {
                overdueBorrowings
                        .computeIfAbsent(reader, k -> new ArrayList<>())
                        .add(borrowing.getId());
                readers.add(reader);
            }
        }

        for (Reader reader : readers) {
            List<Long> overdueBorrowingIds = overdueBorrowings.get(reader);
            if (overdueBorrowingIds != null) {
                List<Borrowing> readerBorrowings = reader.getBorrowings();
                if (readerBorrowings != null) {
                    List<Borrowing> overdueBorrowingsList = readerBorrowings.stream()
                            .filter(borrowing -> overdueBorrowingIds.contains(borrowing.getId()))
                            .collect(Collectors.toList());
                    reader.setBorrowings(overdueBorrowingsList);
                }
            }
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

    public Reader findLatestReaderId() {
        return readerRepository.findFirstByOrderByIdDesc()
                .orElseThrow(ReaderNotFoundException::new);
    }
}
