package com.parkmate.notificationservice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    /**
     * 2xx: 요청 성공
     **/
    SUCCESS(HttpStatus.OK, true, 200, "요청에 성공하였습니다."),

    /**
     * 4xx: 클라이언트 오류
     **/
    BAD_REQUEST(HttpStatus.BAD_REQUEST, false, 400, "잘못된 요청입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, false, 404, "리소스가 존재하지 않습니다."),
    REQUEST_CONFLICT(HttpStatus.CONFLICT, false, 409, "POST 요청에 실패했습니다."),
    INVALID_HEADER_REQUEST(HttpStatus.BAD_REQUEST, false, 400, "유효하지 않은 헤더 요청입니다."),
    INVALID_RECEIVER_TYPE(HttpStatus.BAD_REQUEST, false, 400, "유효하지 않은 receiverType 입니다."),

    /**
     * 5xx: 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 500, "서버 내부 오류가 발생했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final int code;
    private final String message;
}