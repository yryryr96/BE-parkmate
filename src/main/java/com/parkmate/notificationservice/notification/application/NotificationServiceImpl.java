package com.parkmate.notificationservice.notification.application;

import com.parkmate.notificationservice.notification.dto.NotificationEventDto;
import com.parkmate.notificationservice.notification.infrastructure.NotificationReactiveRepository;
import com.parkmate.notificationservice.notification.infrastructure.NotificationRestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRestRepository notificationRestRepository;

    @Transactional
    @Override
    public void createNotification(NotificationEventDto notificationEventDto) {
        notificationRestRepository.save(notificationEventDto.toEntity());
        log.info("Notification created: {}", notificationEventDto);
    }
}
