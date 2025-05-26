package com.parkmate.parking_service.common.exception;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class BaseException extends RuntimeException {

    private final ResponseStatus status;

    public BaseException(ResponseStatus status) {
        this.status = status;
    }

    public BaseException(ResponseStatus status, String message) {
        super(message);
        this.status = status;
    }
}
