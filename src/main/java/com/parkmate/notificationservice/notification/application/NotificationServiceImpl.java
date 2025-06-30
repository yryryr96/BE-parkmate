package com.parkmate.notificationservice.notification.application;

import com.parkmate.notificationservice.common.response.CursorPage;
import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.NotificationStatus;
import com.parkmate.notificationservice.notification.dto.request.NotificationDeleteRequestDto;
import com.parkmate.notificationservice.notification.dto.request.NotificationReadRequestDto;
import com.parkmate.notificationservice.notification.dto.request.NotificationsGetRequestDto;
import com.parkmate.notificationservice.notification.dto.response.NotificationReadResponseDto;
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
    public List<Notification> create(List<Notification> notifications) {
        return notificationRepository.saveAll(notifications);
    }

    @Transactional
    @Override
    public void updateNotificationStatus(Notification notification, NotificationStatus status) {
        notification.updateStatus(status);
        notificationRepository.save(notification);
    }

    @Transactional
    @Override
    public NotificationReadResponseDto readNotificationById(NotificationReadRequestDto notificationReadRequestDto) {

        Notification notification = notificationRepository.readNotification(notificationReadRequestDto);
        return NotificationReadResponseDto.from(notification);
    }

    @Transactional
    @Override
    public void delete(NotificationDeleteRequestDto notificationDeleteRequestDto) {

        notificationRepository.delete(notificationDeleteRequestDto);
    }

    @Transactional(readOnly = true)
    @Override
    public CursorPage<NotificationResponseDto> getNotificationsByReceiverUuid(
            NotificationsGetRequestDto notificationsGetRequestDto) {

        CursorPage<Notification> notifications = notificationRepository.getNotificationsByReceiverUuid(
                notificationsGetRequestDto);
        return notifications.map(NotificationResponseDto::from);
    }

    @Transactional(readOnly = true)
    @Override
    public long getUnreadNotificationCount(String receiverUuid) {
        return notificationRepository.getUnreadNotificationCount(receiverUuid);
    }

    @Transactional
    @Override
    public List<Notification> bulkInsert(List<Notification> notifications) {
        return notificationRepository.bulkInsert(notifications);
    }
}
