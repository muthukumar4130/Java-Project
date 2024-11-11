package com.example.demo.exception;

import com.example.demo.constants.APIConstants;
import com.example.demo.utils.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Log4j2
public class ExceptionControllerAdvice {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private Message message;

    @Value("${api.service.code}")
    private String serviceCode;

    @Value("${internal.errorcode.prefix}")
    private String internalErrorCodePrefix;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody FinalResponse handleAllOtherExceptions(Exception ex, HttpServletRequest request) {

        String errorMessage = ex.getMessage();
        log.info("Exception message: " + errorMessage);
        String errorType = APIConstants.TECHNICAL_ERROR;
        String[] errorMessageParts = errorMessage != null  ? errorMessage.split("-") : new String[0] ;
        if (errorMessageParts.length > 1) {
            errorType = errorMessageParts[0].equalsIgnoreCase("BE") ? APIConstants.BUSINESS_ERROR
                    : APIConstants.TECHNICAL_ERROR;
            errorMessage = errorMessageParts[1];
        }
        log.info(errorMessageParts[0]);
        List<Errors> errorList = commonUtil.getErrorList(serviceCode + "-" + internalErrorCodePrefix + "-" + "2000",
                message.getMessage("2000"), ex.getMessage(), errorType);
        log.info("Error Message" + errorMessage);
        return commonUtil.getErrorResponse("INTERNAL_ERROR", errorList);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ExternalException.class)
    public @ResponseBody FinalResponse handleExternalException(ExternalException ex, HttpServletRequest request) {
        List<Errors> errorList = commonUtil.getErrorList(ex.getErrorCode(), ex.getErrorMessage(), ex.getDescription(),
                ex.getErrorType());
        log.info("Error Message" + ex.getErrorMessage());
        return commonUtil.getErrorResponse("EXTERNAL_ERROR", errorList);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    //@ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    @ExceptionHandler(InternalException.class)
    public @ResponseBody FinalResponse handleInternalException(InternalException ex, HttpServletRequest request) {

        List<Errors> errorList = commonUtil.getErrorList(ex.getErrorCode(), ex.getErrorMessage(), ex.getDescription(),
                ex.getErrorType());
        log.info("Error Message" + ex.getErrorMessage());
        return commonUtil.getErrorResponse("INTERNAL_ERROR", errorList);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public @ResponseBody FinalResponse handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex,
                                                              HttpServletRequest request) {

        String errorMessage = message.getMessage("2405");
        log.info("Exception message: " + errorMessage);
        String errorType = APIConstants.TECHNICAL_ERROR;
        String[] errorMessageParts = errorMessage != null  ? errorMessage.split("-") : new String[0] ;
        if (errorMessageParts.length > 1) {
            errorType = errorMessageParts[0].equalsIgnoreCase("BE") ? APIConstants.BUSINESS_ERROR
                    : APIConstants.TECHNICAL_ERROR;
            errorMessage = errorMessageParts[1];
        }
        List<Errors> errorList = commonUtil.getErrorList(serviceCode + "-" + internalErrorCodePrefix + "2405",
                errorMessage, "", errorType);
        log.info("Error Message" + errorMessage);
        return commonUtil.getErrorResponse("INTERNAL_ERROR", errorList);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody FinalResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                             HttpServletRequest request) {
        String errorMessage = message.getMessage("2400");
        log.info("HandleMethodArgumentNotValidException message: " + errorMessage);
        String errorType = APIConstants.TECHNICAL_ERROR;
        String[] errorMessageParts = errorMessage != null  ? errorMessage.split("-") : new String[0] ;
        if (errorMessageParts.length > 1) {
            errorType = errorMessageParts[0].equalsIgnoreCase("BE") ? APIConstants.BUSINESS_ERROR
                    : APIConstants.TECHNICAL_ERROR;
            errorMessage = errorMessageParts[1];
        }
        List<Errors> errorList = commonUtil.getErrorList(serviceCode + "-" + internalErrorCodePrefix + "2400",
                errorMessage, "", errorType);
        return commonUtil.getErrorResponse("INTERNAL_ERROR", errorList);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TypeMismatchException.class)
    public @ResponseBody FinalResponse handleTypeMismatchException(TypeMismatchException ex,
                                                                   HttpServletRequest request) {

        String errorMessage = message.getMessage("2002");
        log.info("HandleTypeMismatchException message: " + errorMessage);
        String errorType = APIConstants.TECHNICAL_ERROR;
        String[] errorMessageParts = errorMessage != null  ? errorMessage.split("-") : new String[0] ;
        if (errorMessageParts.length > 1) {
            errorType = errorMessageParts[0].equalsIgnoreCase("BE") ? APIConstants.BUSINESS_ERROR
                    : APIConstants.TECHNICAL_ERROR;
            errorMessage = errorMessageParts[1];
        }
        List<Errors> errorList = commonUtil.getErrorList(serviceCode + "-" + internalErrorCodePrefix + "2002",
                errorMessage, "", errorType);
        return commonUtil.getErrorResponse("INTERNAL_ERROR", errorList);
    }
}
