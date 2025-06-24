package com.parkmate.notificationservice.notification.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationDeleteRequestDto {

    private String notificationId;
    private String receiverUuid;

    @Builder
    private NotificationDeleteRequestDto(String notificationId, String receiverUuid) {
        this.notificationId = notificationId;
        this.receiverUuid = receiverUuid;
    }

    public static NotificationDeleteRequestDto of(String notificationId, String receiverUuid) {
        return NotificationDeleteRequestDto.builder()
                .notificationId(notificationId)
                .receiverUuid(receiverUuid)
                .build();
    }
}
