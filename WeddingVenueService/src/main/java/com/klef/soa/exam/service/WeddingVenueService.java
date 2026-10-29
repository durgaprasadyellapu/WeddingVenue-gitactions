package com.klef.soa.exam.service;

import java.util.List;

import com.klef.soa.exam.entity.WeddingVenue;

public interface WeddingVenueService {

    WeddingVenue addWeddingVenue(WeddingVenue venue);

    List<WeddingVenue> displayAllWeddingVenues();

    WeddingVenue displayWeddingVenueById(Long id);

    

   
}

