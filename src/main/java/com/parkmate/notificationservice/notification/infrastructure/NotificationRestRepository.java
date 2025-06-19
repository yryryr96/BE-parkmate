package com.parkmate.notificationservice.notification.infrastructure;

import com.parkmate.notificationservice.notification.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRestRepository extends MongoRepository<Notification, String> {

    List<Notification> findAllByReceiverUuid(String receiverUuid);
}
