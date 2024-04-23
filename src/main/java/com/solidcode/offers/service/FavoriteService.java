package com.solidcode.offers.service;

import java.util.List;
import java.util.stream.Collectors;

import com.solidcode.offers.dto.response.OfferResponse;
import com.solidcode.offers.mapper.OfferMapper;
import com.solidcode.offers.repository.FavoriteRepository;
import com.solidcode.offers.repository.entity.Favorite;
import com.solidcode.offers.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    private FavoriteRepository favoriteRepository;
    private OfferMapper offerMapper;

    @Autowired
    public FavoriteService(final FavoriteRepository favoriteRepository, final OfferMapper offerMapper) {
        this.favoriteRepository = favoriteRepository;
        this.offerMapper = offerMapper;
    }

    public List<OfferResponse> getFavorites(final String userKey) {

        User user = User.builder().userKey(userKey).build();
        List<Favorite> favorites = favoriteRepository.findByUser(user);

        return favorites.stream()
                .map(favorite -> offerMapper.toOfferResponse(favorite.getOffer()))
                .collect(Collectors.toList());
    }
}
