package com.example.lib.repository;

import com.example.lib.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepo extends JpaRepository<Patron, Long> {
}
