package com.solidcode.offers.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/greetings")
public class GreetingController {

    @RequestMapping("/welcome")
    public String welcome() {
        return "Welcome, got some offer for you!";
    }
}
