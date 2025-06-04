package com.parkmate.parkingservice.parkinglot.infrastructure;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglot.domain.ParkingLotGeoLocation;
import com.parkmate.parkingservice.parkinglot.dto.response.ParkingLotGeoResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ParkingLotGeoLocationRepository extends JpaRepository<ParkingLotGeoLocation, Long> {

    @Query(value = """
    SELECT pl.parking_lot_uuid AS parkingLotUuid,
           ST_Y(pl.location) AS latitude,
           ST_X(pl.location) AS longitude,
           ST_Distance_Sphere(
               pl.location,
               ST_SRID(Point(:longitude, :latitude), 4326)
           ) AS distance
    FROM parking_lot_geo_location pl
    WHERE ST_Distance_Sphere(
              pl.location,
              ST_SRID(Point(:longitude, :latitude), 4326)
          ) <= :radius
    """, nativeQuery = true)
    List<ParkingLotGeoResponseDto> findNearbyParkingLots(double latitude,
                                                         double longitude,
                                                         double radius);


    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO parking_lot_geo_location (parking_lot_uuid, location)
        VALUES (:#{#parkingLot.parkingLotUuid},
                ST_SRID(Point(:#{#parkingLot.longitude}, :#{#parkingLot.latitude}), 4326))
        """, nativeQuery = true)
    void insert(ParkingLot parkingLot);
}
