package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinalResponse {

    private String timestamp;
    private String status;
    private String message;
    private String errorCode;
    private String errorMessage;
    private String errorType;
    private Object data;
    private Object error;

}
