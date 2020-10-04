package com.emdadul.restapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emdadul.restapi.entities.Offer;

public interface OfferRespository extends JpaRepository<Offer, Long> {
	@Query(value = "select * from Offer o where o.expiredate >= CURRENT_DATE()" , nativeQuery = true)
	List<Offer> findAll();
    
}
