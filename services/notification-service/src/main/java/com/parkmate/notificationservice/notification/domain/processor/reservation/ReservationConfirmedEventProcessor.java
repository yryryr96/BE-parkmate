package com.parkmate.notificationservice.notification.domain.processor.reservation;

import com.parkmate.notificationservice.common.response.ApiResponse;
import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.NotificationStatus;
import com.parkmate.notificationservice.notification.event.NotificationEvent;
import com.parkmate.notificationservice.notification.event.reservation.ReservationEvent;
import com.parkmate.notificationservice.notification.domain.processor.EventProcessor;
import com.parkmate.notificationservice.notification.event.reservation.ReservationEventType;
import com.parkmate.notificationservice.notification.infrastructure.client.parking.ParkingClient;
import com.parkmate.notificationservice.notification.infrastructure.client.user.UserClient;
import com.parkmate.notificationservice.notification.infrastructure.client.response.ParkingLotHostResponse;
import com.parkmate.notificationservice.notification.infrastructure.client.response.UsernameResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationConfirmedEventProcessor implements EventProcessor<ReservationEvent> {

    private final UserClient userClient;
    private final ParkingClient parkingClient;

    private static final long LAZY_TIME_SECONDS = 5L;
    private static final String TITLE = "예약 완료";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
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
        return event instanceof ReservationEvent && ((ReservationEvent) event).getEventType() == ReservationEventType.CONFIRMED;
    }

    @Override
    public CompletableFuture<List<Notification>> process(ReservationEvent event) {

        Objects.requireNonNull(event, "ReservationCreatedEvent must not be null");

        String userUuid = event.getUserUuid();
        String parkingLotUuid = event.getParkingLotUuid();

        CompletableFuture<ApiResponse<UsernameResponse>> usernameFuture = userClient.getUsername(userUuid)
                .exceptionally(ex -> {
                    log.error("Failed to fetch user name for UUID {}: {}", userUuid, ex.getMessage());
                    return null;
                });

        CompletableFuture<ParkingLotHostResponse> hostFuture = parkingClient.getHostUuid(event.getParkingLotUuid())
                .exceptionally(ex -> {
                    log.error("Failed to fetch host UUID for parking lot {}: {}", event.getParkingLotUuid(), ex.getMessage());
                    return null;
                });

        return CompletableFuture.allOf(usernameFuture, hostFuture)
                .thenApplyAsync(v -> {

                    ApiResponse<UsernameResponse> userNameResponse = usernameFuture.join();
                    ParkingLotHostResponse hostResponse = hostFuture.join();

                    String userName = (userNameResponse != null && userNameResponse.getData() != null)
                            ? userNameResponse.getData().getName()
                            : "회원";

                    String content = getContent(event, userName);
                    LocalDateTime sendAt = LocalDateTime.now().plusSeconds(LAZY_TIME_SECONDS);

                    List<Notification> notifications = new ArrayList<>();

                    notifications.add(createUserNotification(event, content, sendAt));

                    if (hostResponse != null && hostResponse.getHostUuid() != null) {
                        notifications.add(createHostNotification(hostResponse.getHostUuid(), event, content, sendAt));
                    } else {
                        log.warn("Host UUID is missing for parking lot {}. Skipping host notification.", parkingLotUuid);
                    }

                    return notifications;
                });
    }

    /**
     * 알림 내용을 생성합니다.
     * @param event 예약 생성 이벤트
     * @param username 예약자 이름
     * @return 생성된 알림 내용 문자열
     */
    private String getContent(ReservationEvent event, String username) {

        String formattedEntryTime = event.getEntryTime().format(DATE_TIME_FORMATTER);
        String formattedExitTime = event.getExitTime().format(DATE_TIME_FORMATTER);

        return String.format(
                CONTENT_TEMPLATE,
                username,
                event.getVehicleNumber(),
                event.getParkingLotName(),
                event.getParkingSpotName(),
                formattedEntryTime,
                formattedExitTime
        );
    }

    /**
     * 사용자에게 발송될 알림 객체를 생성합니다.
     * @param event 예약 생성 이벤트
     * @param content 알림 내용
     * @param sendAt 알림 발송 시간
     * @return 사용자 알림 객체
     */
    private Notification createUserNotification(ReservationEvent event, String content, LocalDateTime sendAt) {
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
    private Notification createHostNotification(String receiverUuid, ReservationEvent event, String content, LocalDateTime sendAt) {
        return Notification.builder()
                .receiverUuid(receiverUuid)
                .title(TITLE)
                .content(content)
                .redirectUrl(HOST_BASE_REDIRECT_URL + event.getReservationCode())
                .sendAt(sendAt)
                .status(NotificationStatus.PENDING)
                .type(event.getNotificationType())
                .build();
    }
}