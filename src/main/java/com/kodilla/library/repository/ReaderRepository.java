package com.kodilla.library.repository;

import com.kodilla.library.domain.borrowing.Borrowing;
import com.kodilla.library.domain.reader.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReaderRepository extends CrudRepository<Reader, Long> {

    List<Reader> findAll();

    Reader findReaderByBorrowingsContains(Borrowing borrowing);

    Optional<Reader> findFirstByOrderByIdDesc();

}
