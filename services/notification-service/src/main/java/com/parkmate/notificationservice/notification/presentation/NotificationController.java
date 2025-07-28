package com.parkmate.notificationservice.notification.presentation;

import com.parkmate.notificationservice.common.exception.BaseException;
import com.parkmate.notificationservice.common.exception.ResponseStatus;
import com.parkmate.notificationservice.common.response.ApiResponse;
import com.parkmate.notificationservice.common.response.CursorPage;
import com.parkmate.notificationservice.notification.application.NotificationService;
import com.parkmate.notificationservice.notification.dto.request.NotificationDeleteRequestDto;
import com.parkmate.notificationservice.notification.dto.request.NotificationReadRequestDto;
import com.parkmate.notificationservice.notification.dto.request.NotificationsGetRequestDto;
import com.parkmate.notificationservice.notification.dto.response.NotificationResponseDto;
import com.parkmate.notificationservice.notification.vo.request.NotificationsGetRequestVo;
import com.parkmate.notificationservice.notification.vo.response.NotificationReadResponseVo;
import com.parkmate.notificationservice.notification.vo.response.NotificationResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@SecurityRequirement(name = "X-User-UUID")
@SecurityRequirement(name = "X-Host-UUID")
public class NotificationController {

    private static final Map<String, String> RECEIVER_TYPE_HEADER_MAP = Map.of(
            "user", "X-User-UUID",
            "host", "X-Host-UUID"
    );

    private final NotificationService notificationService;

    @Operation(
            summary = "알림 리스트 조회 API",
            description = """
                    - 알림 리스트를 조회합니다. 요청 헤더에 `X-User-UUID` 또는 `X-Host-UUID`를 포함해야 합니다.
                    - `receiverType`은 `user` 또는 `host` 중 하나를 선택해야 합니다.
                    - status가 `SENT`, `READ` 인 알림만 조회됩니다.
                    - type에는 `CHAT_MESSAGE("채팅 메시지")`, `EMPTY_SPOT_AVAILABLE("빈 주차 공간 알림")`,`RESERVATION_CREATED("예약 생성 알림")`,
                      `RESERVATION_MODIFIED("예약 수정 알림")`,`RESERVATION_CANCELED("예약 취소 알림")`,`USER_PARKING_ENTRY("사용자 입차 알림")`,
                      `PARKING_EXIT_REMINDER("주차장 퇴장 알림")` 이 있습니다.
                    """,
            tags = {"NOTIFICATION-SERVICE"}
    )
    @GetMapping("/{receiverType}")
    public ApiResponse<CursorPage<NotificationResponseVo>> getUserNotifications(
            @PathVariable String receiverType,
            @ModelAttribute NotificationsGetRequestVo notificationsGetRequestVo,
            HttpServletRequest request) {

        String receiverUuid = getReceiverUuidFromHeader(receiverType, request);

        return ApiResponse.ok(
                notificationService.getNotificationsByReceiverUuid(
                                NotificationsGetRequestDto.of(receiverUuid, notificationsGetRequestVo))
                        .map(NotificationResponseDto::toVo)
        );
    }

    @Operation(
            summary = "알림 읽음 처리 API",
            description = """
                    - 특정 알림을 읽음 처리합니다. 요청 헤더에 `X-User-UUID` 또는 `X-Host-UUID`를 포함해야 합니다.
                    - `notificationId`는 읽음 처리할 알림의 ID입니다.
                    - `receiverType`은 `user` 또는 `host` 중 하나를 선택해야 합니다.
                    - redirectUrl을 반환하고 해당 URL로 리다이렉트 처리를 클라이언트에서 수행해야 합니다.
                    """,
            tags = {"NOTIFICATION-SERVICE"}
    )
    @PatchMapping("/{notificationId}/{receiverType}")
    public ApiResponse<NotificationReadResponseVo> readNotificationById(@PathVariable String notificationId,
                                                                        @PathVariable String receiverType,
                                                                        HttpServletRequest request) {

        String receiverUuid = getReceiverUuidFromHeader(receiverType, request);

        return ApiResponse.ok(
                notificationService.readNotificationById(
                        NotificationReadRequestDto.of(notificationId, receiverUuid)).toVo()
        );
    }

    @DeleteMapping("/{notificationId}/{receiverType}")
    @Operation(
            summary = "알림 삭제 API",
            description = """
                    - 특정 알림을 삭제합니다. 요청 헤더에 `X-User-UUID` 또는 `X-Host-UUID`를 포함해야 합니다.
                    - `notificationId`는 삭제할 알림의 ID입니다.
                    - `receiverType`은 `user` 또는 `host` 중 하나를 선택해야 합니다.
                    """,
            tags = {"NOTIFICATION-SERVICE"}
    )
    public ApiResponse<Void> deleteNotificationById(@PathVariable String notificationId,
                                                    @PathVariable String receiverType,
                                                    HttpServletRequest request) {


        String receiverUuid = getReceiverUuidFromHeader(receiverType, request);

        notificationService.delete(NotificationDeleteRequestDto.of(notificationId, receiverUuid));

        return ApiResponse.of(HttpStatus.NO_CONTENT, "알림이 삭제되었습니다.", null);
    }

    @GetMapping("/{receiverType}/unread-count")
    @Operation(
            summary = "읽지 않은 알림 개수 조회 API",
            description = """
                    - 특정 사용자의 읽지 않은 알림 개수를 조회합니다. 요청 헤더에 `X-User-UUID` 또는 `X-Host-UUID`를 포함해야 합니다.
                    - `receiverType`은 `user` 또는 `host` 중 하나를 선택해야 합니다.
                    """,
            tags = {"NOTIFICATION-SERVICE"}
    )
    public ApiResponse<Long> getUnreadNotificationCount(
            @PathVariable String receiverType,
            HttpServletRequest request) {

        String receiverUuid = getReceiverUuidFromHeader(receiverType, request);
        return ApiResponse.ok(notificationService.getUnreadNotificationCount(receiverUuid));
    }

    private String getReceiverUuidFromHeader(String receiverType,
                                             HttpServletRequest request) {

        String headerName = RECEIVER_TYPE_HEADER_MAP.get(receiverType);
        if (headerName == null) {
            throw new BaseException(ResponseStatus.INVALID_RECEIVER_TYPE);
        }

        String receiverUuid = request.getHeader(headerName);
        if (receiverUuid == null || receiverUuid.isBlank()) {
            throw new BaseException(ResponseStatus.INVALID_HEADER_REQUEST);
        }

        return receiverUuid;
    }
}
