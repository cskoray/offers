package com.solidcode.offers.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import com.solidcode.offers.dto.response.OfferResponse;
import com.solidcode.offers.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api/favorites")
@Validated
@RequiredArgsConstructor
public class FavoriteController {

    private FavoriteService favoriteService;

    @Autowired
    public FavoriteController(final FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }


    @GetMapping
    @ResponseStatus(OK)
    public List<OfferResponse> getFavorites(@RequestParam("userKey") String userKey) {

        log.info("OfferController: getFavorites user key: {}", userKey);
        return favoriteService.getFavorites(userKey);
    }
}
