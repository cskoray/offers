package com.solidcode.offers.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import com.solidcode.offers.dto.request.MerchantRequest;
import com.solidcode.offers.dto.response.MerchantResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock({
        @ConfigureWireMock(name = "merchant-service", property = "merchant-client.url")
})
class MerchantControllerIntegrationTest {

    @InjectWireMock("merchant-service")
    private WireMockServer merchantService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testSaveMerchant() {
        merchantService.stubFor(post("/v1/api/merchants").willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                        { "name": "amazon", "site": "amazon.com" }
                        """)));

        MerchantRequest merchantRequest = MerchantRequest.builder()
                .name("merchant")
                .site("merchantSite")
                .build();
        ResponseEntity<MerchantResponse> response = restTemplate.postForEntity("/v1/api/merchants", merchantRequest, MerchantResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
    }

    @Test
    void testGetMerchants() {
        merchantService.stubFor(get("/v1/api/merchants").willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                        [
                            { "name": "amazon", "site": "amazon.com" },
                            { "name": "ebay", "site": "ebay.com" }
                        ]
                        """)));

        ResponseEntity<List> response = restTemplate.getForEntity("/v1/api/merchants", List.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
    }
}