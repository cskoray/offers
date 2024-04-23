package com.solidcode.offers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OfferResponse {

    private String offerKey;
    private String description;
    private double discountAmount;
    private String merchantKey;
    private String merchantName;
    private String merchantSite;
    private String merchantLogo;
    private String offerType;
    private String expiryDate;
}
