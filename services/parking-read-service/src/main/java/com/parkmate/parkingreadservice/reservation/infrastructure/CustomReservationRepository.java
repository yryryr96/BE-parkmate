package com.parkmate.parkingreadservice.reservation.infrastructure;

import com.parkmate.parkingreadservice.reservation.dto.response.ReserveParkingLotResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomReservationRepository {

    List<ReserveParkingLotResponseDto> findAllByParkingLotUuidsAndDates(List<String> parkingLotUuids,
                                                                        LocalDateTime startDateTime,
                                                                        LocalDateTime endDateTime);
}
