package com.example.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Entity
@Table(name = "patron")
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name="contact_info")
    private Integer contactInfo;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "patron", cascade = CascadeType.ALL)
    private Set<BorrowingRecord> borrowingRecords;

}
