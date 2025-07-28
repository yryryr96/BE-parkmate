package com.parkmate.parkingservice.parkinglotoptionmapping.infrastructure;

import com.parkmate.parkingservice.parkinglotoptionmapping.domain.ParkingLotOptionMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotOptionMappingRepository extends JpaRepository<ParkingLotOptionMapping, Long> {

    void deleteAllInBatchByParkingLotUuid(String parkingLotUuid);
}
