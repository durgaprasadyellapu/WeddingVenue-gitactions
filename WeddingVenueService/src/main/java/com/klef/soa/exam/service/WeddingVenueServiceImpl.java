
package com.klef.soa.exam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.soa.exam.entity.WeddingVenue;
import com.klef.soa.exam.repository.WeddingVenueRepository;

@Service
public class WeddingVenueServiceImpl implements WeddingVenueService {

    @Autowired
    private WeddingVenueRepository repo;

    @Override
    public WeddingVenue addWeddingVenue(WeddingVenue venue) {
        return repo.save(venue);
    }

   

    @Override
    public List<WeddingVenue> displayAllWeddingVenues() {
        return repo.findAll();
    }

    @Override
    public WeddingVenue displayWeddingVenueById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
    
    
