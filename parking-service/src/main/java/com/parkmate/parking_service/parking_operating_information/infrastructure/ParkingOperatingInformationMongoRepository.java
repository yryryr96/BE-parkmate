package com.parkmate.parking_service.parking_operating_information.infrastructure;

import com.parkmate.parking_service.parking_operating_information.entity.ParkingOperatingInformation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkingOperatingInformationMongoRepository extends MongoRepository<ParkingOperatingInformation, String> {

}
