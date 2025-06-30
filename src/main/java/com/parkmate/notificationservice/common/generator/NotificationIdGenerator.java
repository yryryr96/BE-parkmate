package com.parkmate.notificationservice.common.generator;

import com.github.f4b6a3.uuid.UuidCreator;

public class NotificationIdGenerator {

    public static String generate() {
        return UuidCreator.getTimeOrdered().toString();
    }
}
