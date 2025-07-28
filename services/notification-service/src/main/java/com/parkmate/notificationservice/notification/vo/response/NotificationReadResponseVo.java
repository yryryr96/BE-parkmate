package com.parkmate.notificationservice.notification.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationReadResponseVo {

    private String redirectUrl;

    @Builder
    private NotificationReadResponseVo(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
