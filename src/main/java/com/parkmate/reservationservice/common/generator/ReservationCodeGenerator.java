package com.parkmate.reservationservice.common.generator;

import java.util.UUID;

public abstract class ReservationCodeGenerator {

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
