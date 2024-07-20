package com.tek.exception;

import java.util.HashMap;
import java.util.Map;

import com.tek.config.GenericResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
//
@ControllerAdvice
@RestController
@Configuration
public class GlobalExceptionHandler {

    /// validation exceptions
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<GenericResponse<Object>> handleException(
            MethodArgumentNotValidException e) {
        return GenericResponse.errorOfMap(processvalidationErrors(e));
    }

    /// client exceptions
    @ExceptionHandler(value = com.tek.exception.ClientException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<GenericResponse<Object>> handingRuntimeException(
            com.tek.exception.ClientException exception) {

        Map<String, Object> errMap = new HashMap<String, Object>();
        if (exception.code != null) {
            return GenericResponse.errorWithCoder(exception.key, exception.code);
        }
        errMap.put(exception.key, exception.getLocalizedMessage());
        return GenericResponse.errorOfMap(errMap);
    }

    /// database constraint violation
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<GenericResponse<Object>> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception) {
        return GenericResponse.errorOfException(exception);
    }

    public static Map<String, Object> processErrors(Exception e) {
        Map<String, Object> validationErrorModels = new HashMap<String, Object>();
        validationErrorModels.put("error", e.getLocalizedMessage());

        return validationErrorModels;
    }

    public static Map<String, String> processDataIntegrityViolationException(DataIntegrityViolationException e) {
        Map<String, String> validationErrorModels = new HashMap<String, String>();
        String constraintName = ((ConstraintViolationException) e.getCause()).getConstraintName();

        validationErrorModels.put(constraintName, e.getMostSpecificCause().getMessage());

        return validationErrorModels;
    }

    private Map<String, Object> processvalidationErrors(MethodArgumentNotValidException e) {
        Map<String, Object> validationErrorModels = new HashMap<String, Object>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            validationErrorModels.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return validationErrorModels;
    }
}