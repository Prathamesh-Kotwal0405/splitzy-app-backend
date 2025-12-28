package com.splitzy.appli.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class CreateGroupRequest {

    @NotBlank
    private String name;
}
