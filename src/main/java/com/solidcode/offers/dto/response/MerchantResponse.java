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
public class MerchantResponse {

    private String merchantKey;
    private String name;
    private String site;
    private String logo;
}
