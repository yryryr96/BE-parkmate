package com.parkmate.notificationservice.notification.dto.response;

import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.vo.response.NotificationResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationResponseDto {

    private String title;
    private String content;
    private LocalDateTime sendAt;
    private Boolean isRead;

    @Builder
    private NotificationResponseDto(String title, String content, LocalDateTime sendAt, Boolean isRead) {
        this.title = title;
        this.content = content;
        this.sendAt = sendAt;
        this.isRead = isRead;
    }


    public static NotificationResponseDto from(Notification notification) {
        return NotificationResponseDto.builder()
                .title(notification.getTitle())
                .content(notification.getContent())
                .sendAt(notification.getSendAt())
                .isRead(notification.getIsRead())
                .build();
    }

    public NotificationResponseVo toVo() {
        return NotificationResponseVo.builder()
                .title(title)
                .content(content)
                .sendAt(sendAt)
                .isRead(isRead)
                .build();
    }
}
