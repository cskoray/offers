package com.solidcode.offers.dto.request;

import java.sql.Timestamp;

import com.solidcode.offers.util.OfferType;
import com.solidcode.offers.validator.ValidOfferType;
import com.solidcode.offers.validator.ValidUUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class OfferRequest {

    @NotNull
    @Size(min = 3, max = 50)
    private String description;

    @ValidOfferType
    private OfferType offerType;

    @NotNull
    private double discountAmount;

    @ValidUUID
    private String merchantKey;

    @NotNull
    private Timestamp expiryDate;
}
