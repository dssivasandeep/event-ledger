package com.eventLedger.accountService.controller;

import com.eventLedger.accountService.dto.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse(
                "account-service",
                "UP"
        );
    }
}