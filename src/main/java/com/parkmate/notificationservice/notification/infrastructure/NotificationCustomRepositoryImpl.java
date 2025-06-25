package com.parkmate.notificationservice.notification.infrastructure;

import com.parkmate.notificationservice.common.response.CursorPage;
import com.parkmate.notificationservice.notification.domain.Notification;
import com.parkmate.notificationservice.notification.domain.NotificationStatus;
import com.parkmate.notificationservice.notification.dto.request.NotificationDeleteRequestDto;
import com.parkmate.notificationservice.notification.dto.request.NotificationReadRequestDto;
import com.parkmate.notificationservice.notification.dto.request.NotificationsGetRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationCustomRepositoryImpl implements NotificationCustomRepository {

    private final MongoTemplate mongoTemplate;

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final String ID_FIELD = "_id";
    private static final String RECEIVER_UUID_FIELD = "receiverUuid";
    private static final String STATUS_FIELD = "status";

    @Override
    public Notification readNotification(NotificationReadRequestDto notificationReadRequestDto) {

        Query query = new Query();
        query.addCriteria(Criteria.where(ID_FIELD).is(notificationReadRequestDto.getNotificationId())
                .and(RECEIVER_UUID_FIELD).is(notificationReadRequestDto.getReceiverUuid())
        );

        Update update = new Update();
        update.set(STATUS_FIELD, NotificationStatus.READ);

        return mongoTemplate.findAndModify(query, update, Notification.class);
    }

    @Override
    public void delete(NotificationDeleteRequestDto notificationDeleteRequestDto) {

        Query query = new Query();
        query.addCriteria(Criteria.where(ID_FIELD).is(notificationDeleteRequestDto.getNotificationId())
                .and(RECEIVER_UUID_FIELD).is(notificationDeleteRequestDto.getReceiverUuid())
        );

        Update update = new Update();
        update.set(STATUS_FIELD, NotificationStatus.DELETED);
        mongoTemplate.updateFirst(query, update, Notification.class);
    }

    @Override
    public CursorPage<Notification> getNotificationsByReceiverUuid(NotificationsGetRequestDto notificationsGetRequestDto) {

        int pageSize = notificationsGetRequestDto.getSize() != null ? notificationsGetRequestDto.getSize() : DEFAULT_PAGE_SIZE;
        String cursor = notificationsGetRequestDto.getCursor();

        Query query = new Query();
        query.addCriteria(
                Criteria.where(RECEIVER_UUID_FIELD).is(notificationsGetRequestDto.getReceiverUuid())
                .and(STATUS_FIELD).in(NotificationStatus.SENT, NotificationStatus.READ)
        );

        if (cursor != null) {
            query.addCriteria(Criteria.where(ID_FIELD).lte(cursor));
        }

        query.with(Sort.by(Sort.Direction.DESC, ID_FIELD)).limit(pageSize + 1);

        List<Notification> content = mongoTemplate.find(query, Notification.class);

        String nextCursor = null;
        boolean hasNext = false;

        if (content.size() > pageSize) {
            hasNext = true;
            nextCursor = content.get(pageSize).getId();
            content.remove(pageSize);
        }

        return CursorPage.<Notification>builder()
                .content(content)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

    @Override
    public long getUnreadNotificationCount(String receiverUuid) {

        Query query = new Query();
        query.addCriteria(Criteria.where(RECEIVER_UUID_FIELD).is(receiverUuid)
                .and(STATUS_FIELD).is(NotificationStatus.SENT));

        return mongoTemplate.count(query, Notification.class);
    }
}
