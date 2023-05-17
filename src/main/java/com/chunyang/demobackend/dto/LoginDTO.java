package com.chunyang.demobackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO {

    private String name;

    private String password;
}
