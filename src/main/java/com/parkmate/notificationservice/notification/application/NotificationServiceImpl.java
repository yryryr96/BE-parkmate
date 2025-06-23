package com.parkmate.notificationservice.notification.application;

import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.NotificationStatus;
import com.parkmate.notificationservice.notification.dto.response.NotificationResponseDto;
import com.parkmate.notificationservice.notification.infrastructure.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    @Override
    public Notification create(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Transactional
    @Override
    public void updateNotificationStatus(Notification notification, NotificationStatus status) {
        notification.updateStatus(status);
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NotificationResponseDto> getNotificationsByReceiverUuid(String receiverUuid) {

        List<Notification> notifications = notificationRepository.findByReceiverUuid(receiverUuid);
        return notifications.stream()
                .map(NotificationResponseDto::from)
                .toList();
    }
}
