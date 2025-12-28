package com.splitzy.appli.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SplitDetailRequest {
    @NotNull
    private Long userId;

    @NotNull
    private BigDecimal value;
}
