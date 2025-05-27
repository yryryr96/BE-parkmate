package com.parkmate.parking_service.common.generator;

import java.util.UUID;

public abstract class UUIDGenerator {

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
