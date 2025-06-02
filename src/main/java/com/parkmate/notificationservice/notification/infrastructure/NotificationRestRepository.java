package com.parkmate.notificationservice.notification.infrastructure;

import com.parkmate.notificationservice.notification.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRestRepository extends MongoRepository<Notification, String> {

}
