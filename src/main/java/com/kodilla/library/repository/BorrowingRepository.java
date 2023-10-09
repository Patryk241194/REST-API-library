package com.kodilla.library.repository;


import com.kodilla.library.domain.bookcopy.CopyStatus;
import com.kodilla.library.domain.borrowing.Borrowing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface BorrowingRepository extends CrudRepository<Borrowing, Long> {

    List<Borrowing> findAll();
    List<Borrowing> findAllByReader_Id(Long readerId);
    List<Borrowing> findByReturnDateBeforeAndBookCopy_Status(LocalDate localDate, CopyStatus status);
    List<Borrowing> findByBookCopy_Status(CopyStatus status);

}
