package com.solidcode.offers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import com.solidcode.offers.dto.response.OfferResponse;
import com.solidcode.offers.mapper.OfferMapper;
import com.solidcode.offers.repository.FavoriteRepository;
import com.solidcode.offers.repository.entity.Favorite;
import com.solidcode.offers.repository.entity.Offer;
import com.solidcode.offers.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private OfferMapper offerMapper;

    @InjectMocks
    private FavoriteService favoriteService;

    private User user;
    private Offer offer;
    private Favorite favorite;
    private OfferResponse offerResponse;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserKey("userKey1");

        offer = new Offer();
        offer.setOfferKey("offerKey1");

        favorite = new Favorite();
        favorite.setUser(user);
        favorite.setOffer(offer);

        offerResponse = new OfferResponse();
        offerResponse.setOfferKey("offerKey1");
    }

    @Test
    void getFavorites() {
        when(favoriteRepository.findByUser(any(User.class))).thenReturn(Collections.singletonList(favorite));
        when(offerMapper.toOfferResponse(any(Offer.class))).thenReturn(offerResponse);

        List<OfferResponse> favorites = favoriteService.getFavorites(user.getUserKey());

        assertEquals(1, favorites.size());
        assertEquals(offerResponse, favorites.get(0));
    }
}