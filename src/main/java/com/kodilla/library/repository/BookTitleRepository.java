package com.kodilla.library.repository;


import com.kodilla.library.domain.booktitle.BookTitle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BookTitleRepository extends CrudRepository<BookTitle, Long> {

    List<BookTitle> findAll();

}
