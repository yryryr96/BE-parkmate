package com.parkmate.parking_service.parking_operation.application;

import com.parkmate.parking_service.parking_operation.dto.request.ParkingOperationCreateRequestDto;
import com.parkmate.parking_service.parking_operation.dto.request.ParkingOperationGetRequestDto;
import com.parkmate.parking_service.parking_operation.dto.response.ParkingOperationResponseDto;
import com.parkmate.parking_service.parking_operation.infrastructure.ParkingOperationMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingOperationServiceImpl implements ParkingOperationService {

    private final ParkingOperationMongoRepository parkingOperationMongoRepository;

    @Transactional
    @Override
    public void register(ParkingOperationCreateRequestDto parkingOperationCreateRequestDto) {
        parkingOperationMongoRepository.save(
                parkingOperationCreateRequestDto.toEntity()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParkingOperationResponseDto> getParkingOperations(ParkingOperationGetRequestDto parkingOperationGetRequestDto) {

        return parkingOperationMongoRepository.findAllByParkingLotUuidAndOperationDateBetween(
                        parkingOperationGetRequestDto.getParkingLotUuid(),
                        parkingOperationGetRequestDto.getStartDate(),
                        parkingOperationGetRequestDto.getEndDate())
                .stream()
                .map(ParkingOperationResponseDto::from)
                .toList();
    }
}
