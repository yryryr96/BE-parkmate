package com.parkmate.notificationservice.notification.infrastructure;

import com.parkmate.notificationservice.notification.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends MongoRepository<Notification, String>, NotificationCustomRepository {

    Optional<Notification> findByIdAndReceiverUuid(String notificationId, String receiverUuid);
}
