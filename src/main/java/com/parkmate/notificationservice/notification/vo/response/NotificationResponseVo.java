package com.parkmate.notificationservice.notification.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationResponseVo {

    private String title;
    private String content;
    private LocalDateTime sendAt;
    private boolean isRead;

    @Builder
    private NotificationResponseVo(String title,
                                   String content,
                                   LocalDateTime sendAt,
                                   boolean isRead) {
        this.title = title;
        this.content = content;
        this.sendAt = sendAt;
        this.isRead = isRead;
    }
}
