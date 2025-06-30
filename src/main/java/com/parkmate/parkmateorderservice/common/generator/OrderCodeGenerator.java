package com.parkmate.parkmateorderservice.common.generator;

import java.util.UUID;

public abstract class OrderCodeGenerator {

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
