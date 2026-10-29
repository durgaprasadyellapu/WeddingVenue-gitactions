
package com.klef.soa.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.soa.exam.entity.WeddingVenue;
import java.util.List;

@Repository
public interface WeddingVenueRepository
        extends JpaRepository<WeddingVenue, Long> {

     List<WeddingVenue> findByCity(String city);
}
