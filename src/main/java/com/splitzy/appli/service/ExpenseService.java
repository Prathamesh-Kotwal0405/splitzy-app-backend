package com.splitzy.appli.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import com.splitzy.appli.dto.request.CreateExpenseRequest;
import com.splitzy.appli.dto.request.SplitDetailRequest;
import com.splitzy.appli.entities.ExpenseEntity;
import com.splitzy.appli.entities.ExpenseSplitEntity;
import com.splitzy.appli.entities.UserEntity;
import com.splitzy.appli.entities.UserGroupEntity;
import com.splitzy.appli.exception.ResourceNotFoundException;
import com.splitzy.appli.repository.ExpenseRepository;
import com.splitzy.appli.repository.ExpenseSplitRepository;
import com.splitzy.appli.repository.GroupRepository;
import com.splitzy.appli.repository.UserRepository;
import com.splitzy.appli.util.SecurityUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    
    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addExpense(CreateExpenseRequest request) throws BadRequestException{

        UserEntity payer = SecurityUtil.getCurrentUser();
        UserGroupEntity group = groupRepository.findById(request.getGroupId()).orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        ExpenseEntity expense = new ExpenseEntity();
        expense.setGroup(group);
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setPaidBy(payer);
        expense.setSplitType(request.getSplitType());
        
        ExpenseEntity savedExpense = expenseRepository.save(expense);

        switch (request.getSplitType()) {
            case EQUAL -> handleEqualSplit(savedExpense, request);
            case UNEQUAL -> handleUnequalSplit(savedExpense, request);
            case PERCENTAGE -> handlePercentageSplit(savedExpense, request);
        }
    }


    //----------------------Splitting logic------------------------

    private void handleEqualSplit(ExpenseEntity expenseEntity, CreateExpenseRequest request){
        int members = request.getSplits().size();

        BigDecimal share = expenseEntity.getAmount().divide(BigDecimal.valueOf(members),2,RoundingMode.HALF_UP);

        for(SplitDetailRequest split : request.getSplits()){
            saveSplit(expenseEntity, split.getUserId(), share);
        }
    }

    private void handleUnequalSplit(ExpenseEntity expenseEntity, CreateExpenseRequest request) throws BadRequestException{
        BigDecimal total = request.getSplits().stream().map(SplitDetailRequest::getValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

            if(total.compareTo(expenseEntity.getAmount())!=0){
                throw new BadRequestException("Split amount donot match total amount");
            }

            for(SplitDetailRequest split : request.getSplits()){
                saveSplit(expenseEntity, split.getUserId(), split.getValue());
            }
    }

    private void handlePercentageSplit(ExpenseEntity expenseEntity, CreateExpenseRequest request) throws BadRequestException{
        BigDecimal totalPercent = request.getSplits().stream().map(SplitDetailRequest::getValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(totalPercent.compareTo(BigDecimal.valueOf(100)) != 0){
            throw new BadRequestException("Total percentage must be 100");
        }
        
        for(SplitDetailRequest split : request.getSplits()){
            BigDecimal share = expenseEntity.getAmount().multiply(split.getValue())
                .divide(BigDecimal.valueOf(100),2,RoundingMode.HALF_UP);
            saveSplit(expenseEntity, split.getUserId(), share);
        }
    }


    private void saveSplit(ExpenseEntity expenseEntity, Long userId, BigDecimal amount){
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        ExpenseSplitEntity split = new ExpenseSplitEntity();
        split.setExpense(expenseEntity);
        split.setUser(user);
        split.setShareAmount(amount);
        expenseSplitRepository.save(split);
    }

}
