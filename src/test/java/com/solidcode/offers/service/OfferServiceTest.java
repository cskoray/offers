package com.solidcode.offers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.solidcode.offers.dto.request.OfferRequest;
import com.solidcode.offers.dto.response.OfferResponse;
import com.solidcode.offers.mapper.OfferMapper;
import com.solidcode.offers.repository.FavoriteRepository;
import com.solidcode.offers.repository.OfferRepository;
import com.solidcode.offers.repository.UserRepository;
import com.solidcode.offers.repository.entity.Favorite;
import com.solidcode.offers.repository.entity.Merchant;
import com.solidcode.offers.repository.entity.Offer;
import com.solidcode.offers.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private OfferMapper offerMapper;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MerchantService merchantService;

    @InjectMocks
    private OfferService offerService;

    private Offer offer;
    private OfferResponse offerResponse;
    private User user;
    private Merchant merchant;

    @BeforeEach
    void setUp() {
        offer = new Offer();
        offer.setOfferKey("offerKey1");

        offerResponse = new OfferResponse();
        offerResponse.setOfferKey("offerKey1");

        user = new User();
        user.setUserKey("userKey1");

        merchant = new Merchant();
        merchant.setMerchantKey("merchantKey1");
    }

    @Test
    void saveOffer() {
        OfferRequest offerRequest = new OfferRequest();
        offerRequest.setMerchantKey("merchantKey1"); // assuming OfferRequest has a merchantKey field

        when(offerMapper.toOffer(offerRequest)).thenReturn(offer);
        when(merchantService.getMerchantByMerchantKey(offerRequest.getMerchantKey())).thenReturn(merchant);
        when(offerRepository.save(any(Offer.class))).thenReturn(offer);
        when(offerMapper.toOfferResponse(any(Offer.class))).thenReturn(offerResponse);

        OfferResponse savedOffer = offerService.saveOffer(offerRequest);

        assertEquals(offerResponse, savedOffer);
    }

    @Test
    void getOffers() {
        when(offerRepository.findAllNotExpired()).thenReturn(Collections.singletonList(offer));
        when(offerMapper.toOfferResponse(any(Offer.class))).thenReturn(offerResponse);

        List<OfferResponse> offers = offerService.getOffers();

        assertEquals(1, offers.size());
        assertEquals(offerResponse, offers.get(0));
    }

    @Test
    void getOffer() {
        when(offerRepository.findByOfferKey(any(String.class))).thenReturn(Optional.of(offer));
        when(offerMapper.toOfferResponse(any(Offer.class))).thenReturn(offerResponse);

        OfferResponse foundOffer = offerService.getOffer(offer.getOfferKey());

        assertEquals(offerResponse, foundOffer);
    }

    @Test
    void updateOffer() {
        when(offerRepository.findByOfferKey(any(String.class))).thenReturn(Optional.of(offer));
        when(offerRepository.save(any(Offer.class))).thenReturn(offer);
        when(offerMapper.toOfferResponse(any(Offer.class))).thenReturn(offerResponse);

        OfferResponse updatedOffer = offerService.updateOffer(offer.getOfferKey(), offer);

        assertEquals(offerResponse, updatedOffer);
    }

    @Test
    void makeOfferFavorite() {
        when(offerRepository.findByOfferKey(any(String.class))).thenReturn(Optional.of(offer));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(new Favorite());

        offerService.makeOfferFavorite(offer.getOfferKey());
    }

    @Test
    void deleteFavorite() {
        when(offerRepository.findByOfferKey(any(String.class))).thenReturn(Optional.of(offer));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        offerService.deleteFavorite(offer.getOfferKey());
    }
}