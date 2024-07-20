package com.tek.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class ClientException extends RuntimeException {

    public String key;
    public Integer code;
    public String additionalString;

    public ClientException(String key, String value, Integer code) {
        super(value);
        this.key = key;
        this.code = code;

    }

    public ClientException(String key, String value, Integer code, String additionalString) {
        super(value);
        this.key = key;
        this.code = code;
        this.additionalString = additionalString;

    }

    public ClientException(String key, String value) {
        super(value);
        this.key = key;
    }

}
