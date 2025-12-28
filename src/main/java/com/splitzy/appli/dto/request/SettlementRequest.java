package com.splitzy.appli.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettlementRequest {
    
@NotNull
    private Long groupId;

    @NotNull
    private Long toUserId;

    @NotNull
    @Positive
    private BigDecimal amount;

}
