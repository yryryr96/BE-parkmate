package com.parkmate.notificationservice.notification.vo.response;

import com.parkmate.notificationservice.notification.domain.NotificationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationResponseVo {

    private String notificationId;
    private String title;
    private String content;
    private LocalDateTime sendAt;
    private NotificationStatus status;

    @Builder
    private NotificationResponseVo(String notificationId,
                                   String title,
                                   String content,
                                   LocalDateTime sendAt,
                                   NotificationStatus status) {
        this.notificationId = notificationId;
        this.title = title;
        this.content = content;
        this.sendAt = sendAt;
        this.status = status;
    }
}
