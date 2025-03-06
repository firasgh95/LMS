package com.example.lib.service;

import com.example.lib.model.Patron;
import com.example.lib.repository.PatronRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    @Autowired
    private PatronRepo patronRepo;

    public List<Patron> findAll() {
        return patronRepo.findAll();
    }

    public Optional<Patron> findById(Long id) {
        return patronRepo.findById(id);
    }

    @Transactional
    public Patron save(Patron patron) {
        return patronRepo.save(patron);
    }

    public Boolean existsById(Long id) {
        return patronRepo.existsById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        patronRepo.deleteById(id);
    }
}
