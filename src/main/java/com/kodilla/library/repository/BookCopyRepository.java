package com.kodilla.library.repository;


import com.kodilla.library.domain.bookcopy.BookCopy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BookCopyRepository extends CrudRepository<BookCopy, Long> {

    List<BookCopy> findAll();

    List<BookCopy> findAllByTitle_Id(Long bookTitleId);

    BookCopy findBookCopyByBorrowingId(Long borrowingId);

}
