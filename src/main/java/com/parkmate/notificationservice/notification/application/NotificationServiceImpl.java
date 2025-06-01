package com.parkmate.notificationservice.notification.application;

import com.parkmate.notificationservice.notification.dto.NotificationEventDto;
import com.parkmate.notificationservice.notification.infrastructure.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    @Override
    public void createNotification(NotificationEventDto notificationEventDto) {
        notificationRepository.save(notificationEventDto.toEntity());
    }
}
