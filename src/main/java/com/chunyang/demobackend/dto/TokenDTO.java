package com.chunyang.demobackend.dto;

import lombok.Data;

@Data
public class TokenDTO {

    private String token;

    private UserDTO user;
}