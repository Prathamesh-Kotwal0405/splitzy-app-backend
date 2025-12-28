package com.splitzy.appli.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.splitzy.appli.dto.response.BalanceResponse;
import com.splitzy.appli.entities.ExpenseSplitEntity;
import com.splitzy.appli.entities.SettlementEntity;
import com.splitzy.appli.repository.ExpenseSplitRepository;
import com.splitzy.appli.repository.SettlementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceService{

    private final ExpenseSplitRepository expenseSplitRepository;
    private final SettlementRepository settlementRepository;

    public List<BalanceResponse> calculateGroupBalances(Long groupId){
        
        List<ExpenseSplitEntity> splits = expenseSplitRepository.findByGroupId(groupId);

        Map<Long, BigDecimal> balanceMap = new HashMap<>();

        for(ExpenseSplitEntity split : splits){
            Long payerId = split.getExpense().getPaidBy().getId();
            Long userId = split.getUser().getId();
            BigDecimal share = split.getShareAmount();

            balanceMap.putIfAbsent(payerId, BigDecimal.ZERO);
            balanceMap.putIfAbsent(userId, BigDecimal.ZERO);

            balanceMap.put(userId, balanceMap.get(userId).subtract(share));
            balanceMap.put(payerId, balanceMap.get(payerId).add(share));
        }

        List<SettlementEntity> settlements =
        settlementRepository.findByGroupId(groupId);

        for (SettlementEntity s : settlements) {

            Long fromId = s.getFromUser().getId();
            Long toId = s.getToUser().getId();
            BigDecimal amount = s.getAmount();

            balanceMap.putIfAbsent(fromId, BigDecimal.ZERO);
            balanceMap.putIfAbsent(toId, BigDecimal.ZERO);

            // fromUser paid -> reduce debt
            balanceMap.put(fromId, balanceMap.get(fromId).add(amount));

            // toUser received -> reduce credit
            balanceMap.put(toId, balanceMap.get(toId).subtract(amount));
        }



        return simplifyBalances(balanceMap);
    }

    private List<BalanceResponse> simplifyBalances(Map<Long, BigDecimal> balanceMap){
        List<BalanceResponse> result = new ArrayList<>();

        List<Map.Entry<Long,BigDecimal>> debtors = balanceMap.entrySet()
            .stream()
            .filter(e -> e.getValue().compareTo(BigDecimal.ZERO) < 0)
            .toList();
        
        List<Map.Entry<Long, BigDecimal>> creditors = balanceMap.entrySet()
            .stream()
            .filter(e -> e.getValue().compareTo(BigDecimal.ZERO) > 0)
            .toList();

        int i=0, j=0;

        while (i < debtors.size() && j<creditors.size()) {
            
            var debtor = debtors.get(i);
            var creditor = creditors.get(j);

            BigDecimal settleAmount = debtor.getValue().abs().min(creditor.getValue());

            result.add(new BalanceResponse(debtor.getKey(), creditor.getKey(), settleAmount));

            debtor.setValue(debtor.getValue().add(settleAmount));
            creditor.setValue(creditor.getValue().subtract(settleAmount));

            if(debtor.getValue().compareTo(BigDecimal.ZERO)==0) i++;
            if(creditor.getValue().compareTo(BigDecimal.ZERO)==0) j++;
        }

        return result;
    }
    
}