package com.parkmate.parkingservice.parkingoperation.application;

import com.parkmate.parkingservice.common.exception.BaseException;
import com.parkmate.parkingservice.common.response.ResponseStatus;
import com.parkmate.parkingservice.parkingoperation.dto.request.ParkingOperationRegisterRequestDto;
import com.parkmate.parkingservice.parkingoperation.dto.request.ParkingOperationGetRequestDto;
import com.parkmate.parkingservice.parkingoperation.dto.request.ParkingOperationListGetRequestDto;
import com.parkmate.parkingservice.parkingoperation.dto.request.ParkingOperationUpdateRequestDto;
import com.parkmate.parkingservice.parkingoperation.dto.response.ParkingOperationResponseDto;
import com.parkmate.parkingservice.parkingoperation.domain.ParkingOperation;
import com.parkmate.parkingservice.parkingoperation.infrastructure.ParkingOperationMongoRepository;
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
    public void register(ParkingOperationRegisterRequestDto parkingOperationRegisterRequestDto) {
        parkingOperationMongoRepository.save(
                parkingOperationRegisterRequestDto.toEntity()
        );
    }

    @Transactional
    @Override
    public void update(ParkingOperationUpdateRequestDto parkingOperationUpdateRequestDto) {

        ParkingOperation parkingOperation = parkingOperationMongoRepository.findByParkingLotUuidAndParkingOperationUuid(
                parkingOperationUpdateRequestDto.getParkingLotUuid(),
                parkingOperationUpdateRequestDto.getParkingOperationUuid()
        ).orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        parkingOperationMongoRepository.save(
                createUpdatedParkingOperationEntity(parkingOperation, parkingOperationUpdateRequestDto)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParkingOperationResponseDto> getParkingOperationList(ParkingOperationListGetRequestDto parkingOperationListGetRequestDto) {

        return parkingOperationMongoRepository.findAllByParkingLotUuidAndOperationDateBetween(
                        parkingOperationListGetRequestDto.getParkingLotUuid(),
                        parkingOperationListGetRequestDto.getStartDate(),
                        parkingOperationListGetRequestDto.getEndDate())
                .stream()
                .map(ParkingOperationResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ParkingOperationResponseDto getParkingOperation(ParkingOperationGetRequestDto parkingOperationGetRequestDto) {
        ParkingOperation parkingOperation = parkingOperationMongoRepository.findByParkingLotUuidAndParkingOperationUuid(
                parkingOperationGetRequestDto.getParkingLotUuid(),
                parkingOperationGetRequestDto.getParkingOperationUuid()
        ).orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        return ParkingOperationResponseDto.from(parkingOperation);
    }

    private ParkingOperation createUpdatedParkingOperationEntity(
            ParkingOperation entity,
            ParkingOperationUpdateRequestDto parkingOperationUpdateRequestDto) {

        return ParkingOperation.builder()
                .parkingOperationUuid(entity.getParkingOperationUuid())
                .parkingLotUuid(entity.getParkingLotUuid())
                .parkingOperationUuid(entity.getParkingOperationUuid())
                .operationDate(entity.getOperationDate())
                .validStartTime(parkingOperationUpdateRequestDto.getValidStartTime())
                .validEndTime(parkingOperationUpdateRequestDto.getValidEndTime())
                .baseIntervalMinutes(parkingOperationUpdateRequestDto.getBaseIntervalMinutes())
                .baseFee(parkingOperationUpdateRequestDto.getBaseFee())
                .extraIntervalMinutes(parkingOperationUpdateRequestDto.getExtraIntervalMinutes())
                .extraFee(parkingOperationUpdateRequestDto.getExtraFee())
                .discountRate(parkingOperationUpdateRequestDto.getDiscountRate())
                .build();
    }
}
