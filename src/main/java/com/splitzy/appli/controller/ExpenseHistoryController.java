package com.splitzy.appli.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.splitzy.appli.dto.response.ExpenseHistoryResponse;
import com.splitzy.appli.dto.response.PaginatedResponse;
import com.splitzy.appli.service.ExpenseHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expenses/history")
@RequiredArgsConstructor
public class ExpenseHistoryController {
    
    private final ExpenseHistoryService expenseHistoryService;

    @GetMapping("/group/{groupId}")
    public PaginatedResponse<ExpenseHistoryResponse> getGroupExpenses(@PathVariable Long groupId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return expenseHistoryService.getGroupExpenses(groupId, page, size);
    }

    @GetMapping("/paid-by/{userId}")
    public PaginatedResponse<ExpenseHistoryResponse> getPaidByUser(@PathVariable Long userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return expenseHistoryService.getExpensesPaidByUser(userId, page, size);
    }
}
