package com.solidcode.offers.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.solidcode.offers.integration.UnsplashMock.mockClient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.List;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import com.solidcode.offers.dto.request.MerchantRequest;
import com.solidcode.offers.dto.response.MerchantResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock({
        @ConfigureWireMock(name = "merchant-service", property = "merchant-client.url"),
        @ConfigureWireMock(name = "logoFactory", property = "logoFactory.url")
})
@ActiveProfiles("test")
@EnableFeignClients
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
class MerchantControllerIntegrationTest {

    @InjectWireMock("merchant-service")
    private WireMockServer merchantService;

    @Autowired
    private TestRestTemplate restTemplate;

    @InjectWireMock("logoFactory")
    private WireMockServer logoFactory;

    @BeforeEach
    void setUp() throws IOException {
        mockClient(logoFactory);
    }

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