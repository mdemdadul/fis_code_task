package com.emdadul.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.emdadul.restapi.entities.Offer;
import com.emdadul.restapi.repositories.OfferRespository;

import lombok.RequiredArgsConstructor;

@Service

@RequiredArgsConstructor
public class OfferService {
	 
	private final OfferRespository offerRespository;

	@Query("select o from Offer o where o.expiredate >= CURRENT_DATE()")
	public List<Offer> findAll() {
        return offerRespository.findAll();
    }

    public Optional<Offer> findById(Long id) {
        return offerRespository.findById(id);
    }

    public Offer save(Offer offer) {
        return offerRespository.save(offer);
    }

    public void deleteById(Long id) {
    	offerRespository.deleteById(id);
    }

}
