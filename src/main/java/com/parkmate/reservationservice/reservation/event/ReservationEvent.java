package com.parkmate.reservationservice.reservation.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationEvent {

    private String receiverUuid;
    private String notificationType;
    private String title;
    private String content;

    private static final String TITLE = "주차 예약 알림";
    private static final String CONTENT = "주차 예약이 완료되었습니다. 자세한 내용은 앱을 확인해주세요.";
    private static final String NOTIFICATION_TYPE = "RESERVATION_COMPLETED";

    @Builder
    private ReservationEvent(String receiverUuid,
                             String notificationType,
                             String title,
                             String content) {
        this.receiverUuid = receiverUuid;
        this.notificationType = notificationType;
        this.title = title;
        this.content = content;
    }

    public static ReservationEvent from(String receiverUuid) {
        return ReservationEvent.builder()
                .receiverUuid(receiverUuid)
                .notificationType(NOTIFICATION_TYPE)
                .title(TITLE)
                .content(CONTENT)
                .build();
    }
}
