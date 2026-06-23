package com.eventLedger.accountService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthResponse {

    private String service;

    private String status;
}
