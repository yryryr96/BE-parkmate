package com.parkmate.notificationservice.firebase;


import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController2 {

    private final FCMService fcmService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody PushNotificationRequest request) {
        try {
            String response = fcmService.sendNotification(
                    request.getTargetToken(),
                    request.getTitle(),
                    request.getBody()
            );
            return ResponseEntity.ok("Notification sent successfully: " + response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error sending notification: " + e.getMessage());
        }
    }
}
