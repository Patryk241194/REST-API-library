package com.kodilla.library.domain.bookcopy;

import com.kodilla.library.domain.booktitle.BookTitle;
import com.kodilla.library.domain.borrowing.Borrowing;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "BOOK_COPIES")
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "COPY_ID", unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CopyStatus status;

    private Integer publicationYear;

    @ManyToOne
    @JoinColumn(name = "TITLE_ID")
    private BookTitle title;

    @OneToOne(
            mappedBy = "bookCopy",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private Borrowing borrowing;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCopy bookCopy)) return false;

        if (getPublicationYear() != bookCopy.getPublicationYear()) return false;
        if (getId() != null ? !getId().equals(bookCopy.getId()) : bookCopy.getId() != null) return false;
        if (getStatus() != null ? !getStatus().equals(bookCopy.getStatus()) : bookCopy.getStatus() != null)
            return false;
        return getTitle() != null ? getTitle().equals(bookCopy.getTitle()) : bookCopy.getTitle() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + getPublicationYear();
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        return result;
    }
}
