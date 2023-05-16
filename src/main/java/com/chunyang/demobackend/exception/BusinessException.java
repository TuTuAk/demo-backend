package com.chunyang.demobackend.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ErrorMessage errorMessage;

    private int httpStatus;

    private BusinessError businessError;

    public BusinessException(BusinessError businessError) {
        this.businessError = businessError;
    }

    public BusinessException(BusinessError businessError, Throwable arg0) {
        super(arg0);
        this.businessError = businessError;
    }

    public BusinessException(String message) {
        super(message);
        this.businessError = null;
    }

    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.httpStatus = HttpStatus.BAD_REQUEST.value();
        this.errorMessage = errorMessage;
    }

    public BusinessError getBusinessError() {
        return businessError;
    }
}