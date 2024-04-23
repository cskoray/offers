package com.solidcode.offers.service;

import static com.solidcode.offers.exception.ErrorType.OFFER_NOT_FOUND;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.solidcode.offers.dto.response.OfferResponse;
import com.solidcode.offers.exception.OfferNotFoundException;
import com.solidcode.offers.mapper.OfferMapper;
import com.solidcode.offers.repository.FavoriteRepository;
import com.solidcode.offers.repository.OfferRepository;
import com.solidcode.offers.repository.UserRepository;
import com.solidcode.offers.repository.entity.Favorite;
import com.solidcode.offers.repository.entity.Offer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OfferService {

    private OfferMapper offerMapper;
    private OfferRepository offerRepository;
    private FavoriteRepository favoriteRepository;
    private UserRepository userRepository;

    @Autowired
    public OfferService(OfferMapper offerMapper, OfferRepository offerRepository, final FavoriteRepository favoriteRepository, final UserRepository userRepository) {
        this.offerMapper = offerMapper;
        this.offerRepository = offerRepository;
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
    }

    public OfferResponse saveOffer(final Offer offer) {

        offer.setOfferKey(UUID.randomUUID().toString());
        Offer saved = offerRepository.save(offer);
        return offerMapper.toOfferResponse(saved);
    }

    public List<OfferResponse> getOffers() {

        List<Offer> all = offerRepository.findAllNotExpired();
        return all.stream()
                .map(offer -> offerMapper.toOfferResponse(offer))
                .collect(Collectors.toList());
    }

    public OfferResponse getOffer(final String offerKey) {

        Offer offer = offerRepository.findByOfferKey(offerKey).orElseThrow(() -> new OfferNotFoundException(OFFER_NOT_FOUND, "key"));
        return offerMapper.toOfferResponse(offer);
    }

    public OfferResponse updateOffer(final String offerKey, final Offer offer) {

        Offer offerDb = offerRepository.findByOfferKey(offerKey).orElseThrow(() -> new OfferNotFoundException(OFFER_NOT_FOUND, "key"));
        offerDb.setDescription(offer.getDescription());
        offerDb.setDiscountAmount(offer.getDiscountAmount());
        offerDb.setExpiryDate(offer.getExpiryDate());
        Offer updated = offerRepository.save(offerDb);
        return offerMapper.toOfferResponse(updated);
    }

    public void makeOfferFavorite(final String offerKey) {

        Offer offer = offerRepository.findByOfferKey(offerKey).orElseThrow(() -> new OfferNotFoundException(OFFER_NOT_FOUND, "key"));
        userRepository.findById(1L).ifPresent(user -> {
            Favorite favorite = Favorite.builder()
                    .offer(offer)
                    .user(user)
                    .build();
            Favorite saved = favoriteRepository.save(favorite);
            log.info("OfferService: makeOfferFavorite favorite {}", saved);
        });
    }

    public void deleteFavorite(final String offerKey) {

        Offer offer = offerRepository.findByOfferKey(offerKey).orElseThrow(() -> new OfferNotFoundException(OFFER_NOT_FOUND, "key"));
        userRepository.findById(1L).ifPresent(user -> {
            favoriteRepository.deleteByOfferAndUser(offer, user);
            log.info("OfferService: deleteFavorite offerKey {}", offerKey);
        });
    }
}
