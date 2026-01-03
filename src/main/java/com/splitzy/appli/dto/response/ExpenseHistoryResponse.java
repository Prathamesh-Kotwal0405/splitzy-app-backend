package com.splitzy.appli.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseHistoryResponse {
    
    private Long expenseId;
    private String description;
    private BigDecimal amount;

    private Long groupId;
    private String groupName;

    private Long paidByUserId;
    private String paidByName;

    private LocalDateTime createdAt;
}
