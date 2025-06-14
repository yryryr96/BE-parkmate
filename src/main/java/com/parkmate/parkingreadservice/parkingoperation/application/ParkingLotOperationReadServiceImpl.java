package com.parkmate.parkingreadservice.parkingoperation.application;

import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;
import com.parkmate.parkingreadservice.parkingoperation.infrastructure.ParkingLotOperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingLotOperationReadServiceImpl implements ParkingLotOperationReadService {

    private final ParkingLotOperationRepository parkingLotOperationRepository;

    @Async
    @Override
    public void create(OperationCreateEvent operationCreateEvent) {
        parkingLotOperationRepository.save(operationCreateEvent.toEntity());
    }

    @Override
    public Set<String> findAvailableParkingLotUuids(List<String> parkingLotUuids,
                                                    LocalDateTime requestStart,
                                                    LocalDateTime requestEnd) {

        LocalDate requestStartDate = requestStart.toLocalDate();
        LocalDate requestEndDate = requestEnd.toLocalDate();

        Set<String> availableUuids = new HashSet<>(parkingLotUuids);

        for (LocalDate date = requestStartDate; !date.isAfter(requestEndDate); date = date.plusDays(1)) {

            LocalDateTime dailyStartTime;
            LocalDateTime dailyEndTime;

            if (date.isEqual(requestStartDate)) {
                dailyStartTime = requestStart;
            } else {
                dailyStartTime = date.atStartOfDay();
            }

            if (date.isEqual(requestEndDate)) {
                dailyEndTime = requestEnd;
            } else {
                dailyEndTime = date.atTime(LocalTime.MAX);
            }

            List<String> dailyAvailableUuids = parkingLotOperationRepository.findOperatingUuidsByTimeRange(
                    new ArrayList<>(availableUuids),
                    date.atStartOfDay(),
                    dailyStartTime,
                    dailyEndTime
            );

            availableUuids.retainAll(new HashSet<>(dailyAvailableUuids));

            if (availableUuids.isEmpty()) {
                return Collections.emptySet();
            }
        }

        return availableUuids;
    }
}
