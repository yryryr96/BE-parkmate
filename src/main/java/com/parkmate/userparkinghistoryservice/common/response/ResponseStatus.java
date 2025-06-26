package com.parkmate.userparkinghistoryservice.common.response;

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
    INVALID_ENTRY_REQUEST(HttpStatus.BAD_REQUEST, false, 400, "입차 요청이 유효하지 않습니다. 예약 내역을 확인해주세요."),
    INVALID_EXIT_REQUEST(HttpStatus.BAD_REQUEST, false, 400, "출차 요청이 유효하지 않습니다. 예약 내역을 확인해주세요."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, false, 404, "리소스가 존재하지 않습니다."),
    REQUEST_CONFLICT(HttpStatus.CONFLICT, false, 409, "POST 요청에 실패했습니다."),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, false, 409, "이미 존재하는 데이터입니다."),

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
