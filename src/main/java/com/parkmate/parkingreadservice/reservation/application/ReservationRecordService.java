package com.parkmate.parkingreadservice.reservation.application;

import com.parkmate.parkingreadservice.kafka.event.ReservationCreateEvent;

public interface ReservationRecordService {

    void create(ReservationCreateEvent reservationCreateEvent);
}
