package com.parkmate.parkingservice.parkinglotoption.infrastructure;

import com.parkmate.parkingservice.parkinglotoption.domain.ParkingLotOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingLotOptionRepository extends JpaRepository<ParkingLotOption, Long> {

    List<ParkingLotOption> findAllByIdIn(List<Long> optionIds);
}
