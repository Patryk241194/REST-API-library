package com.kodilla.library.domain.booktitle;

import com.kodilla.library.domain.bookcopy.BookCopy;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TITLES")
public class BookTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "TITLE_ID", unique = true)
    private Long id;
    private String title;
    private String author;

    @OneToMany(
            targetEntity = BookCopy.class,
            mappedBy = "title",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<BookCopy> copies = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookTitle bookTitle)) return false;

        if (getId() != null ? !getId().equals(bookTitle.getId()) : bookTitle.getId() != null) return false;
        if (getTitle() != null ? !getTitle().equals(bookTitle.getTitle()) : bookTitle.getTitle() != null) return false;
        return getAuthor() != null ? getAuthor().equals(bookTitle.getAuthor()) : bookTitle.getAuthor() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        return result;
    }
}


