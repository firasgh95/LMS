package com.example.lib.controller;

import com.example.lib.model.Patron;
import com.example.lib.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping
    public ResponseEntity<List<Patron>> getPatrons() {
        List<Patron> patrons = patronService.findAll();
        return new ResponseEntity<>(patrons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        Optional<Patron> patron = patronService.findById(id);
        return patron.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<Patron> addPatron(@RequestBody Patron patron) {
        Patron newPatron = patronService.save(patron);
        return new ResponseEntity<>(newPatron, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Patron> editPatron(@PathVariable Long id, @RequestBody Patron editedPatron) {
        return patronService.findById(id).map(existingPatron -> {
            existingPatron.setName(editedPatron.getName());
            existingPatron.setContactInfo(editedPatron.getContactInfo());

            Patron updatedPatron = patronService.save(existingPatron);
            return new ResponseEntity<> (updatedPatron, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletePatron(@PathVariable Long id) {
        Boolean isValid = patronService.existsById(id);
        if(isValid)
            patronService.deleteById(id);
        return new ResponseEntity<>(isValid, isValid? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
