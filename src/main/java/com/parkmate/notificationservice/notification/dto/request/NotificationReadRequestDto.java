package com.parkmate.notificationservice.notification.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationReadRequestDto {

    private String notificationId;
    private String receiverUuid;

    @Builder
    private NotificationReadRequestDto(String notificationId, String receiverUuid) {
        this.notificationId = notificationId;
        this.receiverUuid = receiverUuid;
    }

    public static NotificationReadRequestDto of(String notificationId, String receiverUuid) {
        return NotificationReadRequestDto.builder()
                .notificationId(notificationId)
                .receiverUuid(receiverUuid)
                .build();
    }
}
