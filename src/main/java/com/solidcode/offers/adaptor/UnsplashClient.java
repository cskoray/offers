package com.solidcode.offers.adaptor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "unsplash", url = "https://api.unsplash.com")
public interface UnsplashClient {

    @GetMapping("/search/photos")
    String searchPhotos(@RequestParam("query") String query, @RequestParam("client_id") String clientId);

}
