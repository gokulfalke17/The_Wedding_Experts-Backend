package com.twe.theweddingexperts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String testing() {
        return "Prod Env. is Working...";
    }
}
