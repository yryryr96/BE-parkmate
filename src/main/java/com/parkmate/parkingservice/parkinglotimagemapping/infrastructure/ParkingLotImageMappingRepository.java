package com.parkmate.parkingservice.parkinglotimagemapping.infrastructure;

import com.parkmate.parkingservice.parkinglotimagemapping.domain.ParkingLotImageMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingLotImageMappingRepository extends JpaRepository<ParkingLotImageMapping, Long> {

    boolean existsByParkingLotUuid(String parkingLotUuid);

    void deleteByParkingLotUuid(String parkingLotUuid);

    List<ParkingLotImageMapping> findAllByParkingLotUuidOrderByImageIndex(String parkingLotUuid);
}
