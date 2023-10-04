package com.kodilla.library.error;

import com.kodilla.library.error.bookcopy.BookCopyNotFoundException;
import com.kodilla.library.error.booktitle.BookTitleNotFoundException;
import com.kodilla.library.error.borrowing.BorrowingNotFoundException;
import com.kodilla.library.error.reader.ReaderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ReaderNotFoundException.class)
    public ResponseEntity<Object> handleReaderNotFoundException(ReaderNotFoundException readerNotFoundException) {
        return new ResponseEntity<>("Reader with given id doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BorrowingNotFoundException.class)
    public ResponseEntity<Object> handleBorrowingNotFoundException(BorrowingNotFoundException borrowingNotFoundException) {
        return new ResponseEntity<>("Borrowing with given id doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookTitleNotFoundException.class)
    public ResponseEntity<Object> handleBookTitleNotFoundException(BookTitleNotFoundException bookTitleNotFoundException) {
        return new ResponseEntity<>("BookTitle with given id doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookCopyNotFoundException.class)
    public ResponseEntity<Object> handleBookCopyNotFoundException(BookCopyNotFoundException bookCopyNotFoundException) {
        return new ResponseEntity<>("BookCopy with given id doesn't exist", HttpStatus.BAD_REQUEST);
    }

}
