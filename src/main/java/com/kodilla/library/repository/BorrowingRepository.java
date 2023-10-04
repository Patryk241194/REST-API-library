package com.kodilla.library.repository;


import com.kodilla.library.domain.Borrowing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BorrowingRepository extends CrudRepository<Borrowing, Long> {

    List<Borrowing> findAll();

    List<Borrowing> findBorrowingByReader_Id(Long readerId);
    List<Borrowing> findAllByReader_Id(Long readerId);


}
