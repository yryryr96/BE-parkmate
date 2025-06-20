package com.parkmate.notificationservice.firebase;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PushNotificationRequest {
    private String targetToken;
    private String title;
    private String body;
}
