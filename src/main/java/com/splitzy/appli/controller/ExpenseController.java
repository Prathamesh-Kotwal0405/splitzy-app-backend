package com.splitzy.appli.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.splitzy.appli.dto.request.CreateExpenseRequest;
import com.splitzy.appli.service.ExpenseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<String> addExpense(@Valid @RequestBody CreateExpenseRequest request) throws BadRequestException{
        expenseService.addExpense(request);
        return ResponseEntity.ok("Expense added successfully");
    }

}
