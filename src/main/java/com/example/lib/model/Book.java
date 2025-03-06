package com.example.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;

@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<BorrowingRecord> borrowingRecords;

    @Column(name="is_borrowed")
    private Boolean isBorrowed = false;

}
