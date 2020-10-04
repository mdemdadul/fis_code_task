package com.emdadul.restapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.emdadul.restapi.entities.Offer;
import com.emdadul.restapi.exception.OfferNotFoundException;
import com.emdadul.restapi.service.OfferService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/offers")
@Slf4j
@RequiredArgsConstructor
public class OfferAPI {
	
	private final OfferService offerService;

    @GetMapping
    public ResponseEntity<List<Offer>> findAll() {
        return ResponseEntity.ok(offerService.findAll());
    }

    @PostMapping
    public ResponseEntity<Offer> create(@Valid @RequestBody Offer offer) {
        return ResponseEntity.ok(offerService.save(offer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> findById(@PathVariable Long id) throws OfferNotFoundException {
        Optional<Offer> offer = offerService.findById(id);
        if (!offer.isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(offer.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Offer> update(@PathVariable Long id, @Validated @RequestBody Offer offer) {
        if (!offerService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(offerService.save(offer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!offerService.findById(id).isPresent()) {
            log.error("Id " + id + " is not existed");
            return ResponseEntity.badRequest().build();
        }

        offerService.deleteById(id);

        return ResponseEntity.ok().build();
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    
    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleOfferFoundExceptions(OfferNotFoundException ex) {
        System.out.println("handling 404 error on a offer entry");
    }
    
}
