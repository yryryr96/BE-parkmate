package com.parkmate.notificationservice.notification.domain.processor.reservation;

import com.parkmate.notificationservice.common.response.ApiResponse;
import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.NotificationStatus;
import com.parkmate.notificationservice.notification.domain.event.NotificationEvent;
import com.parkmate.notificationservice.notification.domain.event.reservation.ReservationCreatedEvent;
import com.parkmate.notificationservice.notification.domain.processor.EventProcessor;
import com.parkmate.notificationservice.notification.infrastructure.client.user.UserClient;
import com.parkmate.notificationservice.notification.infrastructure.client.user.response.UserNameResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationCreatedEventProcessor implements EventProcessor<ReservationCreatedEvent> {

    private final UserClient userClient;

    private static final long LAZY_TIME_SECONDS = 5L;
    private static final String TITLE = "예약 완료";
    private static final String USER_BASE_REDIRECT_URL = "http://localhost:3000/user/reservations/";
    private static final String HOST_BASE_REDIRECT_URL = "http://localhost:3000/host/reservations/";
    private static final String CONTENT_TEMPLATE = """
            예약이 완료되었습니다.\s
            예약자: %s
            차량 번호: %s
            주차장: %s
            주차 공간: %s
            입차 시간: %s
            출차 시간: %s
            """;

    @Override
    public boolean supports(NotificationEvent event) {
        return event instanceof ReservationCreatedEvent;
    }

    @Override
    public CompletableFuture<List<Notification>> create(ReservationCreatedEvent event) {

        Objects.requireNonNull(event, "ReservationCreatedEvent must not be null");

        String userUuid = event.getUserUuid();
        String reservationCode = event.getReservationCode();

        CompletableFuture<ApiResponse<UserNameResponse>> userNameFuture = userClient.getUserName(userUuid)
                .exceptionally(ex -> {
                    log.error("Failed to fetch user name for UUID {}: {}", userUuid, ex.getMessage());
                    return null;
                });

        return userNameFuture.thenApplyAsync(userNameResponse -> {

            String userName;
            if (userNameResponse == null || userNameResponse.getData() == null) {
                log.warn("User name response is null or data is missing for UUID {}", userUuid);
                userName = "회원";
            } else {
                userName = userNameResponse.getData().getName();
            }

            String content = getContent(event, userName);
            LocalDateTime sendAt = LocalDateTime.now().plusSeconds(LAZY_TIME_SECONDS);

            Notification userNotification = createUserNotification(event, content, sendAt);
            Notification hostNotification = createHostNotification(event, content, sendAt);

            return List.of(userNotification, hostNotification);
        });
    }

    /**
     * 알림 내용을 생성합니다.
     * @param event 예약 생성 이벤트
     * @param userName 예약자 이름
     * @return 생성된 알림 내용 문자열
     */
    private String getContent(ReservationCreatedEvent event, String userName) {
        return String.format(
                CONTENT_TEMPLATE,
                userName,
                event.getVehicleNumber(),
                event.getParkingLotName(),
                event.getParkingSpotName(),
                event.getEntryTime(),
                event.getExitTime()
        );
    }

    /**
     * 사용자에게 발송될 알림 객체를 생성합니다.
     * @param event 예약 생성 이벤트
     * @param content 알림 내용
     * @param sendAt 알림 발송 시간
     * @return 사용자 알림 객체
     */
    private Notification createUserNotification(ReservationCreatedEvent event, String content, LocalDateTime sendAt) {
        return Notification.builder()
                .receiverUuid(event.getUserUuid())
                .title(TITLE)
                .content(content)
                .redirectUrl(USER_BASE_REDIRECT_URL + event.getReservationCode())
                .sendAt(sendAt)
                .status(NotificationStatus.PENDING)
                .type(event.getNotificationType())
                .build();
    }

    /**
     * 호스트에게 발송될 알림 객체를 생성합니다.
     * @param event 예약 생성 이벤트
     * @param content 알림 내용
     * @param sendAt 알림 발송 시간
     * @return 호스트 알림 객체
     */
    private Notification createHostNotification(ReservationCreatedEvent event, String content, LocalDateTime sendAt) {
        return Notification.builder()
                .receiverUuid(event.getHostUuid())
                .title(TITLE)
                .content(content)
                .redirectUrl(HOST_BASE_REDIRECT_URL + event.getReservationCode())
                .sendAt(sendAt)
                .status(NotificationStatus.PENDING)
                .type(event.getNotificationType())
                .build();
    }
}