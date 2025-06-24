package com.parkmate.notificationservice.notification.domain;

import com.parkmate.notificationservice.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @Id
    private String id;
    private String receiverUuid;
    private String title;
    private String content;
    private String redirectUrl;
    private NotificationType type;
    private NotificationStatus status;
    private LocalDateTime sendAt;
    private Boolean isRead;

    @Builder
    private Notification(String id, String receiverUuid, String title, String content, String redirectUrl,
                         NotificationType type, NotificationStatus status, LocalDateTime sendAt, Boolean isRead) {
        this.id = id;
        this.receiverUuid = receiverUuid;
        this.title = title;
        this.content = content;
        this.redirectUrl = redirectUrl;
        this.type = type;
        this.status = status;
        this.sendAt = sendAt;
        this.isRead = isRead;
    }

    public void updateStatus(NotificationStatus status) {
        this.status = status;
    }
}
