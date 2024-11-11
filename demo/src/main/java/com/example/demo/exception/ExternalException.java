package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalException extends RuntimeException {

    private static final long serialVersionUID = -5811352053910961806L;

    private String errorCode;
    private String errorMessage;
    private String description;
    private String errorType;

    public ExternalException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ExternalException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public ExternalException() {
        super();
    }

    public ExternalException(Throwable cause) {
        super(cause);
    }

    public ExternalException(String errorCode, String errorMessage, String description,String errorType) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.description = description;
        this.errorType = errorType;
    }
}
