package com.parkmate.notificationservice.notification.dto.response;

import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.vo.response.NotificationReadResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationReadResponseDto {

    private String redirectUrl;

    @Builder
    private NotificationReadResponseDto(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public static NotificationReadResponseDto from(Notification notification) {
        return NotificationReadResponseDto.builder()
                .redirectUrl(notification.getRedirectUrl())
                .build();
    }

    public NotificationReadResponseVo toVo() {
        return NotificationReadResponseVo.builder()
                .redirectUrl(redirectUrl)
                .build();
    }
}
