package com.splitzy.appli.dto.request;

import java.math.BigDecimal;
import java.util.List;

import com.splitzy.appli.entities.SplitType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateExpenseRequest {
    
    @NotNull
    private Long groupId;

    private String description;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private SplitType splitType;

    @NotEmpty
    private List<SplitDetailRequest> splits;
}
