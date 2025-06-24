package com.parkmate.notificationservice.notification.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationsGetRequestVo {

    private String cursor;
    private Integer size;
}
