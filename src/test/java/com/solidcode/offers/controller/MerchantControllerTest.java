package com.solidcode.offers.controller;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solidcode.offers.dto.request.MerchantRequest;
import com.solidcode.offers.dto.response.MerchantResponse;
import com.solidcode.offers.mapper.MerchantMapper;
import com.solidcode.offers.repository.entity.Merchant;
import com.solidcode.offers.service.MerchantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MerchantController.class)
class MerchantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MerchantService merchantService;

    @MockBean
    private MerchantMapper merchantMapper;

    @BeforeEach
    void setUp() {

        MerchantResponse merchantResponse = MerchantResponse.builder().merchantKey("merchantKey1").name("some name").site("some site").build();
        Merchant merchant = Merchant.builder().merchantKey("merchantKey1").name("some name").site("some site").build();
        MerchantRequest merchantRequest = MerchantRequest.builder().name("some name").site("some site").build();
        when(merchantService.getMerchant("merchantKey1")).thenReturn(merchantResponse);
        when(merchantService.getMerchants()).thenReturn(singletonList(merchantResponse));
        when(merchantService.saveMerchant(merchant)).thenReturn(merchantResponse);
        when(merchantService.updateMerchant("merchantKey1", merchant)).thenReturn(merchantResponse);
        when(merchantMapper.toMerchantResponse(merchant)).thenReturn(merchantResponse);
        when(merchantMapper.toMerchant(merchantRequest)).thenReturn(merchant);
    }

    @Test
    void getMerchant() throws Exception {
        mockMvc.perform(get("/v1/api/merchants/merchant/{merchantKey}", "merchantKey1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("some name"));
    }

    @Test
    void getMerchants() throws Exception {
        mockMvc.perform(get("/v1/api/merchants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("some name"));
    }

    @Test
    void saveMerchant() throws Exception {
        MerchantRequest merchantRequest = MerchantRequest.builder()
                .name("some name")
                .site("some site")
                .build();
        mockMvc.perform(post("/v1/api/merchants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(merchantRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("some name"));
    }

    @Test
    void updateMerchant() throws Exception {
        MerchantRequest merchantRequest = MerchantRequest.builder()
                .name("updated name")
                .site("some site")
                .build();
        mockMvc.perform(put("/v1/api/merchants/merchant/{merchantKey}", "merchantKey1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(merchantRequest)))
                .andExpect(status().isOk());
    }
}