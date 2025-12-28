package com.splitzy.appli.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddGroupMemberRequest {

    @NotNull
    private Long userId;
}

