package com.solidcode.offers.dto.request;

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
public class MerchantRequest {

    @NotNull
    @Size(min = 3, max = 35)
    private String name;

    @NotNull
    private String site;
}
