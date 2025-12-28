package com.splitzy.appli.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BalanceResponse {
    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;
}
