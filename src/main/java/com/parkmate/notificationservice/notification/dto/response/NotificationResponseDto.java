package com.parkmate.notificationservice.notification.dto.response;

import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.NotificationStatus;
import com.parkmate.notificationservice.notification.vo.response.NotificationResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationResponseDto {

    private String notificationId;
    private String title;
    private String content;
    private LocalDateTime sendAt;
    private NotificationStatus status;

    @Builder
    private NotificationResponseDto(String notificationId, String title, String content, LocalDateTime sendAt,
                                    NotificationStatus status) {
        this.notificationId = notificationId;
        this.title = title;
        this.content = content;
        this.sendAt = sendAt;
        this.status = status;
    }


    public static NotificationResponseDto from(Notification notification) {
        return NotificationResponseDto.builder()
                .notificationId(notification.getId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .sendAt(notification.getSendAt())
                .status(notification.getStatus())
                .build();
    }

    public NotificationResponseVo toVo() {
        return NotificationResponseVo.builder()
                .notificationId(notificationId)
                .title(title)
                .content(content)
                .sendAt(sendAt)
                .status(status)
                .build();
    }
}
