package com.example.demo.utils;

import com.example.demo.constants.APIConstants;
import com.example.demo.exception.Errors;
import com.example.demo.exception.FinalResponse;
import com.example.demo.exception.InternalException;
import com.example.demo.exception.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

@Log4j2
@Component
@RequiredArgsConstructor
public class CommonUtil {

    @Autowired
    private Message message;

    public FinalResponse getSuccessResponse(String message, Object data) {
        return FinalResponse.builder().timestamp(LocalDateTime.now().toString()).status(APIConstants.SUCCESS)
                .message(message).data(data).build();
    }

    public FinalResponse getErrorResponse(String errorMessage, List<Errors> errorList) {
        return FinalResponse.builder().timestamp(LocalDateTime.now().toString()).status(APIConstants.ERROR)
                .message(errorMessage).errorCode(errorList.get(0).getErrorCode())
                .errorMessage(errorList.get(0).getMessage())
                .error(errorList).errorType(errorList.get(0).getErrorType()).build();
    }

    public List<Errors> getErrorList(String errorCode, String errorMessage, String description, String errorType) {
        Errors errors = Errors.builder().errorCode(errorCode).errorMessage(errorMessage)
                .message(errorCode + ": " + errorMessage).description(description).errorType(errorType).build();
        List<Errors> errorList = new ArrayList<>();
        errorList.add(errors);
        return errorList;
    }

    public void handleInternalError(String errorCode, String apiName, Exception ex) throws Exception {

        String errorMessage = message.getMessage(errorCode);
        String errorType = APIConstants.TECHNICAL_ERROR;
        String errorDescription = "";
        String[] errorMessageParts = errorMessage != null ? errorMessage.split("-") : new String[0];
        if (errorMessageParts.length > 1) {
            errorType = errorMessageParts[0].equalsIgnoreCase("BE") ? APIConstants.BUSINESS_ERROR
                    : APIConstants.TECHNICAL_ERROR;
            errorMessage = errorMessageParts[1];
        }
        if (ex instanceof DataAccessException || ex instanceof IOException
                || ex instanceof MissingResourceException) { // Handle
            errorDescription = ex.getMessage();
        } else {
            log.error("Exception occurred: {}", ex.getMessage());
        }
        log.error("handleInternalError()-apiName=" + apiName + "; errorCode=" + errorCode + "; errorMessage=" + errorMessage +"; errorType="+errorType);
        throw new InternalException(errorCode, errorMessage, errorDescription,errorType);
    }
}
