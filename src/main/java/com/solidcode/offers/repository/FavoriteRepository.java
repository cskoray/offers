package com.solidcode.offers.repository;

import com.solidcode.offers.repository.entity.Favorite;
import com.solidcode.offers.repository.entity.Offer;
import com.solidcode.offers.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    void deleteByOfferAndUser(Offer offer, User user);
}
