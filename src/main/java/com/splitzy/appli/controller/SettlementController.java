package com.splitzy.appli.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitzy.appli.dto.request.SettlementRequest;
import com.splitzy.appli.service.SettlementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/settlements")
@RequiredArgsConstructor
public class SettlementController {
    
    private final SettlementService settlementService;

    @PostMapping
    public ResponseEntity<String> settle(@Valid @RequestBody SettlementRequest request) {
        settlementService.settle(request);
        return ResponseEntity.ok("Settlement recorded successfully");
    }
}
