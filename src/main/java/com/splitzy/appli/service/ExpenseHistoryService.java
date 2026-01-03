package com.splitzy.appli.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.splitzy.appli.dto.response.ExpenseHistoryResponse;
import com.splitzy.appli.dto.response.PaginatedResponse;
import com.splitzy.appli.entities.ExpenseEntity;
import com.splitzy.appli.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseHistoryService {
    
    private final ExpenseRepository expenseRepository;

    // public List<ExpenseHistoryResponse> getGroupExpenses(Long groupId){
    //     return expenseRepository.findByGroupIdOrderByCreatedAtDesc(groupId)
    //             .stream()
    //             .map(this::mapToResponse)
    //             .toList();
    // }

    // public List<ExpenseHistoryResponse> getExpensesPaidByUser(Long userId){
    //     return expenseRepository.findByPaidByIdOrderByCreatedAtDesc(userId)
    //             .stream()
    //             .map(this::mapToResponse)
    //             .toList();
    // }

    public PaginatedResponse<ExpenseHistoryResponse> getGroupExpenses(Long groupId, int page, int size){
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<ExpenseEntity> expensePage = expenseRepository.findByGroupId(groupId, pageable);

        List<ExpenseHistoryResponse> responses = expensePage.getContent().stream().map(this::mapToResponse).toList();

        return new PaginatedResponse<>(
            responses,
            expensePage.getNumber(),
            expensePage.getSize(),
            expensePage.getTotalElements(),
            expensePage.getTotalPages(),
            expensePage.isLast()
        );

    }

    public PaginatedResponse<ExpenseHistoryResponse> getExpensesPaidByUser(Long userId, int page, int size){
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<ExpenseEntity> expensePage = expenseRepository.findByPaidById(userId, pageable);

        List<ExpenseHistoryResponse> responses = expensePage.getContent().stream().map(this::mapToResponse).toList();

        return new PaginatedResponse<>(
            responses,
            expensePage.getNumber(),
            expensePage.getSize(),
            expensePage.getTotalElements(),
            expensePage.getTotalPages(),
            expensePage.isLast()
        );

    }

    private ExpenseHistoryResponse mapToResponse(ExpenseEntity expense){
        return new ExpenseHistoryResponse(
            expense.getId(),
            expense.getDescription(),
            expense.getAmount(),
            expense.getGroup().getId(),
            expense.getGroup().getName(),
            expense.getPaidBy().getId(),
            expense.getPaidBy().getName(),
            expense.getCreatedAt()

        );
    }
}
