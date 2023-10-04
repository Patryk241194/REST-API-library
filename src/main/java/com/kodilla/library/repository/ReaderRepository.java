package com.kodilla.library.repository;

import com.kodilla.library.domain.Borrowing;
import com.kodilla.library.domain.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ReaderRepository extends CrudRepository<Reader, Long> {

    List<Reader> findAll();

    Reader findReaderByBorrowingsContains(Borrowing borrowing);

}
