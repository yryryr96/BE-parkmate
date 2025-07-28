package com.parkmate.parkingreadservice.reservation.application;

import com.parkmate.parkingreadservice.kafka.event.reservation.ReservationEvent;
import com.parkmate.parkingreadservice.reservation.dto.response.ReserveParkingLotResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRecordService {

    void create(ReservationEvent reservationEvent);

    void update(ReservationEvent reservationEvent);

    List<ReserveParkingLotResponseDto> getParkingLotUuidsByUuidsAndDates(List<String> parkingLotUuids,
                                                                         LocalDateTime startDateTime,
                                                                         LocalDateTime endDateTime);
}
