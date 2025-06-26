package com.parkmate.parkingservice.parkingspot.infrastructure;

import com.parkmate.parkingservice.parkingspot.domain.ParkingSpot;
import com.parkmate.parkingservice.parkingspot.domain.ParkingSpotType;
import com.parkmate.parkingservice.parkingspot.dto.SpotIdAndTypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    void deleteByIdAndParkingLotUuid(Long parkingSpotId,
                                     String parkingLotUuid
    );

    Optional<ParkingSpot> findByIdAndParkingLotUuid(Long parkingSpotId,
                                                    String parkingLotUuid);

    List<ParkingSpot> findAllByParkingLotUuid(String parkingLotUuid);

    List<ParkingSpot> findAllByParkingLotUuidAndType(String parkingLotUuid,
                                        ParkingSpotType parkingSpotType);

    List<ParkingSpot> findAllByIdIn(List<Long> parkingSpotIds);

    @Query(value = "SELECT new com.parkmate.parkingservice.parkingspot.dto.SpotIdAndTypeDto(ps.id, ps.type) " +
            "FROM ParkingSpot ps " +
            "WHERE ps.parkingLotUuid = :parkingLotUuid")
    List<SpotIdAndTypeDto> findSpotIdAndTypes(String parkingLotUuid);
}
