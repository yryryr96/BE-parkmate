package com.parkmate.parkmateorderservice.common.exception;

import com.parkmate.parkmateorderservice.common.response.ApiResponse;
import com.parkmate.parkmateorderservice.common.response.ResponseStatus;
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
        log.info("예외 발생 - 상태: {}, 메시지: {}", status, e.getMessage());
        if (ResponseStatus.RESOURCE_NOT_FOUND.equals(e.getStatus())) {
            return new ResponseEntity<>(
                    ApiResponse.error(e.getStatus()),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                ApiResponse.error(e.getStatus()),
                status
        );
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ApiResponse<Object>> RuntimeError(RuntimeException e) {

        BaseException response = new BaseException(ResponseStatus.INTERNAL_SERVER_ERROR);
        for (StackTraceElement s : e.getStackTrace()) {
            System.out.println(s);
        }

        return new ResponseEntity<>(
                ApiResponse.error(response.getStatus()),
                response.getStatus().getHttpStatus()
        );
    }
}