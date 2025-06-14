package com.parkmate.parkingreadservice.reservation.application;

import com.parkmate.parkingreadservice.kafka.event.ReservationCreateEvent;
import com.parkmate.parkingreadservice.reservation.dto.response.ReserveParkingLotResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRecordService {

    void create(ReservationCreateEvent reservationCreateEvent);

    List<ReserveParkingLotResponseDto> getParkingLotUuidsByUuidsAndDates(List<String> parkingLotUuids,
                                                                         LocalDateTime startDateTime,
                                                                         LocalDateTime endDateTime);
}
