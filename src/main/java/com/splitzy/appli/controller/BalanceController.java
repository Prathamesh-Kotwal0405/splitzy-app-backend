package com.splitzy.appli.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitzy.appli.dto.response.BalanceResponse;
import com.splitzy.appli.service.BalanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/balances")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<BalanceResponse>> groupBalances(@PathVariable Long groupId){
        return ResponseEntity.ok(balanceService.calculateGroupBalances(groupId));
    }
    
}
