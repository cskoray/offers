package com.solidcode.offers.adaptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogoFactory {

    @Value("${unsplash.access.key}")
    String clientId;

    @Autowired
    private UnsplashClient unsplashClient;

    public String getLogo(String merchantName) {

        System.out.println("----------->LogoFactory: getLogo merchant name: " + merchantName + " clientId: " + clientId);
        log.info("----------->LogoFactory: getLogo merchant name: {}, client_id {}", merchantName, clientId);
        String result = unsplashClient.searchPhotos(merchantName, clientId);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(result);
            return jsonNode.get("results").get(0).get("urls").get("small").asText();
        } catch (JsonProcessingException e) {
            return "https://via.placeholder.com/150";
        }
    }
}
