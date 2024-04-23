package com.solidcode.offers.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import com.solidcode.offers.dto.request.MerchantRequest;
import com.solidcode.offers.dto.response.MerchantResponse;
import com.solidcode.offers.mapper.MerchantMapper;
import com.solidcode.offers.repository.entity.Merchant;
import com.solidcode.offers.service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/v1/api/merchants")
@Validated
@RequiredArgsConstructor
public class MerchantController {

    private MerchantService merchantService;
    private MerchantMapper merchantMapper;

    @Autowired
    public MerchantController(MerchantService merchantService, MerchantMapper merchantMapper) {
        this.merchantService = merchantService;
        this.merchantMapper = merchantMapper;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public MerchantResponse saveMerchant(@Valid @RequestBody MerchantRequest request) {

        log.info("MerchantController: saveMerchant request: {}", request.toString());
        Merchant merchant = merchantMapper.toMerchant(request);
        return merchantService.saveMerchant(merchant);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<MerchantResponse> getMerchants() {

        log.info("MerchantController: getAllMerchant");
        return merchantService.getMerchants();
    }

    @GetMapping("/merchant/{merchantKey}")
    @ResponseStatus(OK)
    public MerchantResponse getMerchant(@PathVariable("merchantKey") String merchantKey) {

        log.info("MerchantController: getMerchant merchant key: {}", merchantKey);
        return merchantService.getMerchant(merchantKey);
    }

    @PutMapping("/merchant/{merchantKey}")
    @ResponseStatus(OK)
    public MerchantResponse updateMerchant(@PathVariable("merchantKey") String merchantKey, @Valid @RequestBody MerchantRequest request) {

        log.info("MerchantController: updateMerchant");
        Merchant merchant = merchantMapper.toMerchant(request);
        return merchantService.updateMerchant(merchantKey, merchant);

    }
}
