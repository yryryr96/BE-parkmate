package com.parkmate.notificationservice.notification.domain.processor;

import com.parkmate.notificationservice.common.response.ApiResponse;
import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.NotificationStatus;
import com.parkmate.notificationservice.notification.domain.event.NotificationEvent;
import com.parkmate.notificationservice.notification.domain.event.ReservationCreatedEvent;
import com.parkmate.notificationservice.notification.infrastructure.client.parkinglot.ParkingLotClient;
import com.parkmate.notificationservice.notification.infrastructure.client.parkinglot.response.ParkingLotAndSpotResponse;
import com.parkmate.notificationservice.notification.infrastructure.client.parkinglot.response.ParkingLotHostResponse;
import com.parkmate.notificationservice.notification.infrastructure.client.reservation.ReservationClient;
import com.parkmate.notificationservice.notification.infrastructure.client.reservation.response.ReservationResponse;
import com.parkmate.notificationservice.notification.infrastructure.client.user.UserClient;
import com.parkmate.notificationservice.notification.infrastructure.client.user.response.UserNameResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationCreatedEventProcessor implements EventProcessor<ReservationCreatedEvent> {

    private final ReservationClient reservationClient;
    private final ParkingLotClient parkingLotClient;
    private final UserClient userClient;

    private static final long LAZY_TIME_SECONDS = 10L;
    private static final String TITLE = "예약 완료";
    private static final String CONTENT_FORMAT = """
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

        String userUuid = event.getUserUuid();

        CompletableFuture<ApiResponse<ReservationResponse>> reservationFuture = reservationClient.getReservationDetails(
                event.getReservationUuid(), userUuid);
        CompletableFuture<ParkingLotAndSpotResponse> parkingLotAndSpotFuture = parkingLotClient.getParkingLotAndParkingSpotDetails(
                event.getParkingLotUuid(), event.getParkingSpotId()
        );
        CompletableFuture<ApiResponse<UserNameResponse>> userNameFuture = userClient.getUserName(userUuid);
        CompletableFuture<ParkingLotHostResponse> hostUuidFuture = parkingLotClient.getParkingLotHostUuid(event.getParkingLotUuid());

        return CompletableFuture.allOf(reservationFuture, parkingLotAndSpotFuture, userNameFuture, hostUuidFuture)
                .thenApplyAsync(v -> {

                    ReservationResponse reservationResponse = reservationFuture.join().getData();
                    ParkingLotAndSpotResponse parkingLotAndSpotResponse = parkingLotAndSpotFuture.join();
                    UserNameResponse userNameResponse = userNameFuture.join().getData();
                    String hostUuid = hostUuidFuture.join().getHostUuid();

                    String content = String.format(
                            CONTENT_FORMAT,
                            userNameResponse.getName(),
                            reservationResponse.getVehicleNumber(),
                            parkingLotAndSpotResponse.getParkingLotName(),
                            parkingLotAndSpotResponse.getParkingSpotName(),
                            reservationResponse.getEntryTime(),
                            reservationResponse.getExitTime()
                    );

                    Notification userNotification = Notification.builder()
                            .title(TITLE)
                            .content(content)
                            .receiverUuid(userUuid)
                            .isRead(false)
                            .sendAt(LocalDateTime.now().plusSeconds(LAZY_TIME_SECONDS))
                            .status(NotificationStatus.PENDING)
                            .type(event.getNotificationType())
                            .build();

                    Notification hostNotification = Notification.builder()
                            .title(TITLE)
                            .content(content)
                            .receiverUuid(hostUuid)
                            .isRead(false)
                            .sendAt(LocalDateTime.now().plusSeconds(LAZY_TIME_SECONDS))
                            .status(NotificationStatus.PENDING)
                            .type(event.getNotificationType())
                            .build();

                    return List.of(userNotification, hostNotification);
                }
        );
    }
}
