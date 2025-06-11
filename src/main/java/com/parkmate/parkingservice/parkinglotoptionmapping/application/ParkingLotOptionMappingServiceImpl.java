package com.parkmate.parkingservice.parkinglotoptionmapping.application;

import com.parkmate.parkingservice.parkinglot.domain.ParkingLot;
import com.parkmate.parkingservice.parkinglotoptionmapping.domain.ParkingLotOptionMapping;
import com.parkmate.parkingservice.parkinglotoptionmapping.dto.request.ParkingLotMappingUpdateRequestDto;
import com.parkmate.parkingservice.parkinglotoptionmapping.dto.response.ParkingLotOptionMappingResponseDto;
import com.parkmate.parkingservice.parkinglotoptionmapping.infrastructure.ParkingLotOptionMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotOptionMappingServiceImpl implements ParkingLotOptionMappingService {

    private final ParkingLotOptionMappingRepository parkingLotOptionMappingRepository;

    @Transactional
    @Override
    public ParkingLotOptionMappingResponseDto update(ParkingLotMappingUpdateRequestDto parkingLotMappingUpdateRequestDto) {

        String parkingLotUuid = parkingLotMappingUpdateRequestDto.getParkingLotUuid();
        parkingLotOptionMappingRepository.deleteAllInBatchByParkingLotUuid(parkingLotUuid);

        List<ParkingLotOptionMapping> optionMappingList = parkingLotMappingUpdateRequestDto.getOptionIds()
                .stream()
                .map(optionId -> ParkingLotOptionMapping.builder()
                        .parkingLotUuid(parkingLotUuid)
                        .optionId(optionId)
                        .build())
                .toList();

        List<ParkingLotOptionMapping> parkingLotOptionMappings = parkingLotOptionMappingRepository.saveAll(optionMappingList);

        return ParkingLotOptionMappingResponseDto.of(
                parkingLotUuid,
                parkingLotOptionMappings.stream()
                        .map(ParkingLotOptionMapping::getOptionId)
                        .toList()
        );
    }
}
