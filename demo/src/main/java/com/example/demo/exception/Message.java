package com.example.demo.exception;

import com.example.demo.constants.APIConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

@Log4j2
@Component
public class Message {

    private String defaultLocale = APIConstants.LOCALE_MESSAGE_ENGLISH;
    private String defaultCountryCode = APIConstants.LOCALE_MESSAGE_COUNTRYCODE;

    public String getMessage(String messageKey) {
        String messageValue = null;
        try {
            ResourceBundle bundle = ResourceBundle
                    .getBundle("messages" + "_" + defaultLocale + "_" + defaultCountryCode);
            messageValue = bundle.getString(messageKey);
        } catch (MissingResourceException ex) {
            log.error("getMessage: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("getMessage: {}", ex.getMessage());
        }
        return null != messageValue ? messageValue : messageKey;
    }

    public String getMessage(String messageKey, String locale, String countryCode) {
        String messageValue = null;
        locale = (Objects.nonNull(locale)) ? locale : defaultLocale;
        countryCode = (Objects.nonNull(countryCode)) ? countryCode : defaultCountryCode;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("messages" + "_" + locale + "_" + countryCode);
            messageValue = bundle.getString(messageKey);
        } catch (MissingResourceException ex) {
            log.error("getMessage: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("getMessage: {}", ex.getMessage());
        }
        return null != messageValue ? messageValue : messageKey;
    }
}
