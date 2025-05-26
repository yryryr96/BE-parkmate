package com.parkmate.parking_service.parking_operating_information.application;

import com.parkmate.parking_service.parking_operating_information.dto.ParkingOperatingInformationCreateRequestDto;
import com.parkmate.parking_service.parking_operating_information.infrastructure.ParkingOperatingInformationMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingOperatingInformationServiceImpl implements ParkingOperatingInformationService {

    private final ParkingOperatingInformationMongoRepository parkingOperatingInformationMongoRepository;

    @Override
    public void register(ParkingOperatingInformationCreateRequestDto parkingOperatingInformationCreateRequestDto) {
        parkingOperatingInformationMongoRepository.save(
                parkingOperatingInformationCreateRequestDto.toEntity()
        );
        log.info("운영시간 저장");
    }
}
