

package com.klef.soa.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.soa.exam.entity.WeddingVenue;
import com.klef.soa.exam.service.WeddingVenueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/wedding")
@RequiredArgsConstructor
public class WeddingController {

    private final WeddingVenueService service;

    @GetMapping("/")
    public String demo() {
        return "Wedding Venue Spring Boot Project";
    }

    @PostMapping("/add")
    public ResponseEntity<WeddingVenue> addWeddingVenue(
            @RequestBody WeddingVenue venue) {

    	WeddingVenue savedVenue = service.addWeddingVenue(venue);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVenue);
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

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Wedding Venue ID Not Found");
    }

   

   

    
}
