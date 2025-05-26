package com.parkmate.parking_service.parkinglot.infrastructure.mysql;

import com.parkmate.parking_service.parkinglot.domain.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
}
