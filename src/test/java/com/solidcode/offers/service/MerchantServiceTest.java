package com.solidcode.offers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.solidcode.offers.adaptor.LogoFactory;
import com.solidcode.offers.dto.response.MerchantResponse;
import com.solidcode.offers.mapper.MerchantMapper;
import com.solidcode.offers.repository.MerchantRepository;
import com.solidcode.offers.repository.entity.Merchant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MerchantServiceTest {

    @Mock
    private MerchantRepository merchantRepository;

    @Mock
    private MerchantMapper merchantMapper;

    @Mock
    private LogoFactory logoFactory;

    @InjectMocks
    private MerchantService merchantService;

    private Merchant merchant;
    private MerchantResponse merchantResponse;

    @BeforeEach
    void setUp() {
        merchant = new Merchant();
        merchant.setMerchantKey("merchantKey1");

        merchantResponse = new MerchantResponse();
        merchantResponse.setMerchantKey("merchantKey1");
    }

    @Test
    void getMerchants() {
        when(merchantRepository.findAll()).thenReturn(Collections.singletonList(merchant));
        when(merchantMapper.toMerchantResponse(any(Merchant.class))).thenReturn(merchantResponse);

        List<MerchantResponse> merchants = merchantService.getMerchants();

        assertEquals(1, merchants.size());
        assertEquals(merchantResponse, merchants.get(0));
    }

    @Test
    void getMerchant() {
        when(merchantRepository.findByMerchantKey(any(String.class))).thenReturn(Optional.of(merchant));
        when(merchantMapper.toMerchantResponse(any(Merchant.class))).thenReturn(merchantResponse);

        MerchantResponse foundMerchant = merchantService.getMerchant(merchant.getMerchantKey());

        assertEquals(merchantResponse, foundMerchant);
    }

    @Test
    void saveMerchant() {
        when(merchantRepository.save(any(Merchant.class))).thenReturn(merchant);
        when(merchantMapper.toMerchantResponse(any(Merchant.class))).thenReturn(merchantResponse);

        MerchantResponse savedMerchant = merchantService.saveMerchant(merchant);

        assertEquals(merchantResponse, savedMerchant);
    }

    @Test
    void updateMerchant() {
        when(merchantRepository.findByMerchantKey(any(String.class))).thenReturn(Optional.of(merchant));
        when(merchantRepository.save(any(Merchant.class))).thenReturn(merchant);
        when(merchantMapper.toMerchantResponse(any(Merchant.class))).thenReturn(merchantResponse);

        MerchantResponse updatedMerchant = merchantService.updateMerchant(merchant.getMerchantKey(), merchant);

        assertEquals(merchantResponse, updatedMerchant);
    }
}