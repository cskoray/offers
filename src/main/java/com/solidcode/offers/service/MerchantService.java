package com.solidcode.offers.service;

import static com.solidcode.offers.exception.ErrorType.MERCHANT_NOT_FOUND;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.solidcode.offers.adaptor.LogoFactory;
import com.solidcode.offers.dto.response.MerchantResponse;
import com.solidcode.offers.exception.MerchantNotFoundException;
import com.solidcode.offers.mapper.MerchantMapper;
import com.solidcode.offers.repository.MerchantRepository;
import com.solidcode.offers.repository.entity.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {

    private MerchantRepository merchantRepository;
    private MerchantMapper merchantMapper;
    private LogoFactory logoFactory;

    @Autowired
    public MerchantService(MerchantRepository merchantRepository, MerchantMapper merchantMapper, final LogoFactory logoFactory) {
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
        this.logoFactory = logoFactory;
    }

    public MerchantResponse saveMerchant(Merchant merchant) {

        String merchantName = merchant.getName();
        String logo = logoFactory.getLogo(merchantName);
        merchant.setLogo(logo);
        merchant.setMerchantKey(UUID.randomUUID().toString());
        Merchant saved = merchantRepository.save(merchant);
        return merchantMapper.toMerchantResponse(saved);
    }

    public List<MerchantResponse> getMerchants() {
        List<Merchant> all = merchantRepository.findAll();
        return all.stream()
                .map(merchant -> merchantMapper.toMerchantResponse(merchant))
                .collect(Collectors.toList());
    }

    public MerchantResponse getMerchant(String merchantKey) {

        Merchant merchant = merchantRepository.findByMerchantKey(merchantKey).orElseThrow(() -> new MerchantNotFoundException(MERCHANT_NOT_FOUND, "key"));
        return merchantMapper.toMerchantResponse(merchant);
    }

    public MerchantResponse updateMerchant(String merchantKey, Merchant merchant) {

        Merchant merchantDb = merchantRepository.findByMerchantKey(merchantKey).orElseThrow(() -> new MerchantNotFoundException(MERCHANT_NOT_FOUND, "key"));
        merchantDb.setName(merchant.getName());
        merchantDb.setLogo(merchant.getLogo());
        Merchant updated = merchantRepository.save(merchantDb);
        return merchantMapper.toMerchantResponse(updated);
    }
}
