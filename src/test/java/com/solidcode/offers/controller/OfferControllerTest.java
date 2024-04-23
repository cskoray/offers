package com.solidcode.offers.controller;

import static org.assertj.core.util.DateUtil.now;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solidcode.offers.dto.request.OfferRequest;
import com.solidcode.offers.dto.response.OfferResponse;
import com.solidcode.offers.mapper.OfferMapper;
import com.solidcode.offers.repository.entity.Offer;
import com.solidcode.offers.service.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(OfferController.class)
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    @MockBean
    private OfferMapper offerMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        OfferResponse offerResponse = OfferResponse.builder()
                .offerKey("offerKey")
                .description("some name")
                .merchantKey("merchantKey")
                .offerType("DISCOUNT")
                .discountAmount(10.0)
                .merchantLogo("merchantLogo")
                .merchantName("merchantName")
                .merchantSite("merchantSite").build();

        OfferRequest offerRequest = OfferRequest.builder().description("some name").build();
        Offer offer = Offer.builder().offerKey("offerKey").description("some name").build();
        when(offerService.saveOffer(offerRequest)).thenReturn(offerResponse);
        when(offerService.updateOffer("offerKey", offer)).thenReturn(offerResponse);
        when(offerService.getOffer("offerKey")).thenReturn(offerResponse);
        when(offerService.getOffers()).thenReturn(Collections.singletonList(offerResponse));
        when(offerMapper.toOfferResponse(offer)).thenReturn(offerResponse);
        when(offerMapper.toOffer(offerRequest)).thenReturn(offer);
    }

    @Test
    void getOffer() throws Exception {

        mockMvc.perform(get("/v1/api/offers/{offerKey}", "offerKey")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("some name"));
    }

    @Test
    void getOffers() throws Exception {
        mockMvc.perform(get("/v1/api/offers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].description").value("some name"));
    }

    @Test
    void saveOffer() throws Exception {
        OfferRequest offerRequest = OfferRequest.builder().type("DISCOUNT").expiryDate(new Timestamp(now().getTime())).description("some name").merchantKey(UUID.randomUUID().toString()).build();
        OfferResponse offerResponse = OfferResponse.builder().description("some name").build();
        when(offerService.saveOffer(offerRequest)).thenReturn(offerResponse);
        mockMvc.perform(post("/v1/api/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("some name"));
    }

    @Test
    void updateOffer() throws Exception {
        String key = UUID.randomUUID().toString();
        OfferRequest offerRequest = new OfferRequest();
        offerRequest.setDescription("updated name");
        offerRequest.setMerchantKey(key);
        offerRequest.setType("DISCOUNT");
        offerRequest.setExpiryDate(new Timestamp(now().getTime()));
        Offer offer = Offer.builder().offerKey("offerKey").description("some name").build();
        when(offerMapper.toOffer(offerRequest)).thenReturn(offer);
        when(offerService.updateOffer("offerKey", offer)).thenReturn(OfferResponse.builder().description("some name").merchantKey("merchantKey").build());

        mockMvc.perform(put("/v1/api/offers/{offerKey}", "offerKey")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("some name"));
    }

    @Test
    void makeOfferFavorite() throws Exception {
        mockMvc.perform(post("/v1/api/offers/{offerKey}/favorite", "offerKey")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFavorite() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/offers/{offerKey}/favorite", "offerKey")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}