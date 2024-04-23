package com.solidcode.offers.mapper;

import com.solidcode.offers.dto.request.MerchantRequest;
import com.solidcode.offers.dto.response.MerchantResponse;
import com.solidcode.offers.repository.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MerchantMapper {

    Merchant toMerchant(MerchantRequest merchantRequest);

    MerchantResponse toMerchantResponse(Merchant merchant);

}
