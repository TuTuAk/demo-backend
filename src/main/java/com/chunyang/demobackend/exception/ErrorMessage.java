package com.chunyang.demobackend.exception;

import lombok.Data;

@Data
public class ErrorMessage {

    private Integer code;

    private String message;
}