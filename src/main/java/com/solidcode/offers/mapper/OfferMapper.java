package com.solidcode.offers.mapper;

import com.solidcode.offers.dto.request.OfferRequest;
import com.solidcode.offers.dto.response.OfferResponse;
import com.solidcode.offers.repository.entity.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OfferMapper {

    Offer toOffer(OfferRequest request);

    @Mapping(target = "merchantName", source = "merchant.name")
    @Mapping(target = "merchantSite", source = "merchant.site")
    @Mapping(target = "merchantLogo", source = "merchant.logo")
    @Mapping(target = "offerType", source = "type")
    @Mapping(target = "merchantKey", source = "merchant.merchantKey")
    OfferResponse toOfferResponse(Offer offer);
}
