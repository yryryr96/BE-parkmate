package com.parkmate.parkingservice.facade.parkingspot;

import com.parkmate.parkingservice.feign.ReservationClient;
import com.parkmate.parkingservice.parkinglot.application.ParkingLotService;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkingspot.application.ParkingSpotService;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import com.parkmate.parkingservice.parkingspot.dto.request.ParkingSpotClientRequest;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotClientResponse;
import com.parkmate.parkingservice.parkingspot.dto.response.ParkingSpotSimpleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingSpotFacade {

    private final ParkingLotService parkingLotService;
    private final ParkingSpotService parkingSpotService;
    private final ReservationClient reservationClient;

    @Transactional(readOnly = true)
    public ParkingSpotClientResponse getParkingSpotsByParkingLotUuidAndType(
            ParkingSpotClientRequest parkingSpotClientRequest) {

        String parkingLotUuid = parkingSpotClientRequest.getParkingLotUuid();
        List<ParkingSpotSimpleResponseDto> parkingSpots = parkingSpotService.getParkingSpotsByParkingLotUuidAndType(
                parkingLotUuid,
                parkingSpotClientRequest.getParkingSpotType()
        );

        ParkingLot parkingLot = parkingLotService.findByUuid(parkingLotUuid);

        return ParkingSpotClientResponse.of(
                parkingLot,
                parkingSpots
        );
    }

    @Transactional(readOnly = true)
    public Map<ParkingSpotType, Long> getAvailableSpotsPerType(String parkingLotUuid, LocalDateTime entryTime,
                                                               LocalDateTime exitTime) {

        Map<Long, ParkingSpotType> spotIdAndTypes = parkingSpotService.findSpotIdAndTypes(parkingLotUuid);
        List<Long> parkingSpotIds = reservationClient.getReservations(parkingLotUuid, entryTime, exitTime);

        for (Long parkingSpotId : parkingSpotIds) {
            spotIdAndTypes.remove(parkingSpotId);
        }

        return spotIdAndTypes.values().stream()
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()));
    }
}
