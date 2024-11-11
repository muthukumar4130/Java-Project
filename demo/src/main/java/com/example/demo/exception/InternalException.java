package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalException extends RuntimeException {

    private static final long serialVersionUID = 4087930070289795747L;

    private String errorCode;
    private String errorMessage;
    private String description;
    private String errorType;

    public InternalException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public InternalException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public InternalException() {
        super();
    }

    public InternalException(Throwable cause) {
        super(cause);
    }

    public InternalException(String errorCode, String errorMessage, String description,String errorType) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.description = description;
        this.errorType = errorType;
    }
}
