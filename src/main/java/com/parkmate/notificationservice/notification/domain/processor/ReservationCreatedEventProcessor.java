package com.parkmate.notificationservice.notification.domain.processor;

import com.parkmate.notificationservice.common.response.ApiResponse;
import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.NotificationStatus;
import com.parkmate.notificationservice.notification.domain.event.NotificationEvent;
import com.parkmate.notificationservice.notification.domain.event.ReservationCreatedEvent;
import com.parkmate.notificationservice.notification.infrastructure.client.parkinglot.ParkingLotClient;
import com.parkmate.notificationservice.notification.infrastructure.client.parkinglot.response.ParkingLotAndSpotResponse;
import com.parkmate.notificationservice.notification.infrastructure.client.reservation.ReservationClient;
import com.parkmate.notificationservice.notification.infrastructure.client.reservation.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationCreatedEventProcessor implements EventProcessor<ReservationCreatedEvent> {

    private final ReservationClient reservationClient;
    private final ParkingLotClient parkingLotClient;

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
    public CompletableFuture<Notification> create(ReservationCreatedEvent event) {

        CompletableFuture<ApiResponse<ReservationResponse>> reservationFuture = reservationClient.getReservationDetails(
                event.getReservationUuid(), event.getUserUuid());

        CompletableFuture<ParkingLotAndSpotResponse> parkingLotAndSpotFuture = parkingLotClient.getParkingLotAndParkingSpotDetails(
                event.getParkingLotUuid(), event.getParkingSpotId()
        );

        return CompletableFuture.allOf(reservationFuture, parkingLotAndSpotFuture).thenApplyAsync(v -> {

                    ReservationResponse reservationResponse = reservationFuture.join().getData();
                    ParkingLotAndSpotResponse parkingLotAndSpotResponse = parkingLotAndSpotFuture.join();

                    String content = String.format(
                            CONTENT_FORMAT,
                            "username",
                            reservationResponse.getVehicleNumber(),
                            parkingLotAndSpotResponse.getParkingLotName(),
                            parkingLotAndSpotResponse.getParkingSpotName(),
                            reservationResponse.getEntryTime(),
                            reservationResponse.getExitTime()
                    );

                    return Notification.builder()
                            .title(TITLE)
                            .content(content)
                            .receiverUuid(event.getUserUuid())
                            .isRead(false)
                            .sendAt(LocalDateTime.now().plusSeconds(LAZY_TIME_SECONDS))
                            .status(NotificationStatus.PENDING)
                            .type(event.getNotificationType())
                            .build();
                }
        ).exceptionally(
                ex -> {
                    log.error("예약 생성 이벤트 처리 중 오류 발생: {}", ex.getMessage());
                    return null;
                }
        );
    }
}
