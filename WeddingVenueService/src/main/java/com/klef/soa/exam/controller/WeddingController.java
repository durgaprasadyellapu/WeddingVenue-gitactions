
package com.klef.soa.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.soa.exam.entity.WeddingVenue;
import com.klef.soa.exam.service.WeddingVenueService;

@RestController
@RequestMapping("/wedding")
public class WeddingController {

    @Autowired
    private WeddingVenueService service;

    @GetMapping("/")
    public String demo() {
        return "Wedding Venue Spring Boot Project";
    }

    
    @PostMapping("/add")
    public ResponseEntity<WeddingVenue> addWeddingVenue(
            @RequestBody WeddingVenue venue) {
        WeddingVenue savedVenue = service.addWeddingVenue(venue);
        return ResponseEntity.status(201).body(savedVenue);
    }

    
    @GetMapping("/displayall")
    public ResponseEntity<List<WeddingVenue>> displayAllWeddingVenues() {
        List<WeddingVenue> venues = service.displayAllWeddingVenues();
        return ResponseEntity.status(200).body(venues);
    }

    
    @GetMapping("/display")
    public ResponseEntity<?> displayWeddingVenueById(
            @RequestParam Long id) {
        WeddingVenue venue = service.displayWeddingVenueById(id);

        if (venue != null) {
            return ResponseEntity.status(200).body(venue);
        } else {
            return ResponseEntity.status(404)
                    .body("Wedding Venue ID Not Found");
        }
    }

    
  
    
}  
   
