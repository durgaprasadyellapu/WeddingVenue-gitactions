
package com.klef.soa.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klef.soa.exam.entity.WeddingVenue;
import com.klef.soa.exam.service.WeddingVenueService;

@RestController
@RequestMapping("/wedding")
@CrossOrigin(origins = "http://localhost:5173")
public class WeddingController {

    private final WeddingVenueService service;

    public WeddingController(WeddingVenueService service) {
        this.service = service;
    }

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
        return ResponseEntity.ok(service.displayAllWeddingVenues());
    }

    @GetMapping("/display")
    public ResponseEntity<?> displayWeddingVenueById(
            @RequestParam Long id) { 
        WeddingVenue venue = service.displayWeddingVenueById(id);

        if (venue != null) {
            return ResponseEntity.ok(venue);
        }

        return ResponseEntity.status(404)
                .body("Wedding Venue ID Not Found");
    }
}
