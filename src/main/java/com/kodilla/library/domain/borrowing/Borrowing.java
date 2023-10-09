package com.kodilla.library.domain.borrowing;

import com.kodilla.library.domain.reader.Reader;
import com.kodilla.library.domain.bookcopy.BookCopy;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "BORROWINGS")
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "BORROWING_ID", unique = true)
    private Long id;
    private LocalDate borrowingDate;
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "READER_ID")
    private Reader reader;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "BOOK_COPY_ID")
    private BookCopy bookCopy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrowing borrowing = (Borrowing) o;
        return Objects.equals(id, borrowing.id) &&
                Objects.equals(borrowingDate, borrowing.borrowingDate) &&
                Objects.equals(returnDate, borrowing.returnDate);
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getBorrowingDate() != null ? getBorrowingDate().hashCode() : 0);
        result = 31 * result + (getReturnDate() != null ? getReturnDate().hashCode() : 0);
        return result;
    }
}
