package com.solidcode.offers.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import com.solidcode.offers.dto.request.OfferRequest;
import com.solidcode.offers.dto.response.OfferResponse;
import com.solidcode.offers.mapper.OfferMapper;
import com.solidcode.offers.repository.entity.Offer;
import com.solidcode.offers.service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api/offers")
@Validated
@RequiredArgsConstructor
public class OfferController {

    private OfferService offerService;
    private OfferMapper offerMapper;

    @Autowired
    public OfferController(OfferService offerService, OfferMapper offerMapper) {
        this.offerService = offerService;
        this.offerMapper = offerMapper;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public OfferResponse saveOffer(@Valid @RequestBody OfferRequest request) {
        log.info("OfferController: saveOffer request: {}", request.toString());
        return offerService.saveOffer(request);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<OfferResponse> getOffers() {
        log.info("OfferController: getOffers");
        return offerService.getOffers();
    }

    @GetMapping("/{offerKey}")
    @ResponseStatus(OK)
    public OfferResponse getOffer(@PathVariable("offerKey") String offerKey) {
        log.info("OfferController: getOffer offerKey: {}", offerKey);
        return offerService.getOffer(offerKey);
    }

    @PutMapping("/{offerKey}")
    @ResponseStatus(OK)
    public OfferResponse updateOffer(@PathVariable("offerKey") String offerKey, @Valid @RequestBody OfferRequest request) {
        log.info("OfferController: updateOffer offerKey: {} request: {}", offerKey, request.toString());
        Offer offer = offerMapper.toOffer(request);
        return offerService.updateOffer(offerKey, offer);
    }

    @PostMapping("/{offerKey}/favorite")
    @ResponseStatus(OK)
    public void makeOfferFavorite(@PathVariable("offerKey") String offerKey) {
        log.info("OfferController: makeOfferFavorite offerKey: {}", offerKey);
        offerService.makeOfferFavorite(offerKey);
    }

    @DeleteMapping("/{offerKey}/favorite")
    @ResponseStatus(OK)
    public void deleteFavorite(@PathVariable("offerKey") String offerKey) {
        log.info("OfferController: deleteFavorite offerKey: {}", offerKey);
        offerService.deleteFavorite(offerKey);
    }
}
