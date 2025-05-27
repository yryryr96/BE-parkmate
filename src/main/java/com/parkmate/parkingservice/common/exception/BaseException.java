package com.parkmate.parkingservice.common.exception;

import com.parkmate.parkingservice.common.response.ResponseStatus;
import lombok.Getter;

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
