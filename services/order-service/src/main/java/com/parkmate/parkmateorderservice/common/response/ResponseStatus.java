package com.parkmate.parkmateorderservice.common.response;

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
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, false, 404, "리소스가 존재하지 않습니다."),
    REQUEST_CONFLICT(HttpStatus.CONFLICT, false, 409, "POST 요청에 실패했습니다."),
    MODIFY_TIME_LIMIT_EXCEEDED(HttpStatus.UNPROCESSABLE_ENTITY, false, 422, "수정 가능한 시간이 초과되었습니다. 예약은 입장 1시간 전까지 수정 가능합니다."),
    PARKING_LOT_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, false, 400, "해당 주차장은 현재 이용할 수 없습니다."),
    INVALID_ORDER_STATUS(HttpStatus.BAD_REQUEST, false, 400, "주문 상태가 유효하지 않습니다."),
    /**
     * 5xx: 서버 오류
     */
    NOT_FOUND_EVENT_DISPATCHER(HttpStatus.NOT_FOUND, false, 500, "이벤트 디스패처를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 500, "서버 내부 오류가 발생했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final int code;
    private final String message;
}