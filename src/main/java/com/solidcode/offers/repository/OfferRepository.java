package com.solidcode.offers.repository;

import java.util.List;
import java.util.Optional;

import com.solidcode.offers.repository.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    Optional<Offer> findByOfferKey(String offerKey);

    @Query("SELECT o FROM Offer o WHERE o.expiryDate > CURRENT_TIMESTAMP")
    List<Offer> findAllNotExpired();
}
