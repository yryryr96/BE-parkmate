package com.parkmate.parkingservice.parkingoperation.infrastructure;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomOperationRepository {

    List<String> getOpenParkingLotUuids(List<String> parkingLotUuids, LocalDateTime now);
}
