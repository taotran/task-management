package com.pycogroup.taotran.task.management.core.rest.error;

import com.pycogroup.taotran.task.management.core.rest.ApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalResourceExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalResourceExceptionHandler.class);

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse unknownException(RuntimeException e, WebRequest request) {
        LOGGER.info(e.toString());

        if (e instanceof AuthenticationException || e instanceof AccessDeniedException) {
            return new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(), 401, "Authorization to access resource failed!");
        }

        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, e.getMessage());
    }
}
