package com.parkmate.notificationservice.common.exception;


import com.parkmate.notificationservice.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ApiResponse<Object>> BaseError(BaseException e) {

        HttpStatus status = e.getStatus().getHttpStatus();

        if (ResponseStatus.RESOURCE_NOT_FOUND.equals(e.getStatus())) {
            return new ResponseEntity<>(
                    ApiResponse.error(e.getStatus(), e.getMessage()),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                ApiResponse.error(e.getStatus(), e.getMessage()),
                status
        );
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ApiResponse<Object>> RuntimeError(RuntimeException e) {

        BaseException response = new BaseException(ResponseStatus.INTERNAL_SERVER_ERROR);
        for (StackTraceElement s : e.getStackTrace()) {
            log.error("Exception occurred: {}. Stack trace: {}", e.getMessage(), s);
        }

        return new ResponseEntity<>(
                ApiResponse.error(response.getStatus()),
                response.getStatus().getHttpStatus()
        );
    }
}