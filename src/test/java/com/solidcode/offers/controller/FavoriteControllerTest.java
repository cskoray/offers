package com.solidcode.offers.controller;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.solidcode.offers.dto.response.OfferResponse;
import com.solidcode.offers.service.FavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(FavoriteController.class)
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavoriteService favoriteService;

    @BeforeEach
    void setUp() {
        OfferResponse offerResponse = new OfferResponse();
        offerResponse.setOfferKey("offerKey1");
        when(favoriteService.getFavorites("userKey1")).thenReturn(singletonList(offerResponse));
    }

    @Test
    void getFavorites() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/favorites")
                        .param("userKey", "userKey1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"offerKey\":\"offerKey1\"}]"));
    }
}