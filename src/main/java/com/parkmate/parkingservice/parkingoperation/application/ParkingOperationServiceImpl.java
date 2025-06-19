package com.parkmate.parkingservice.parkingoperation.application;

import com.parkmate.parkingservice.common.exception.BaseException;
import com.parkmate.parkingservice.common.response.ResponseStatus;
import com.parkmate.parkingservice.kafka.event.OperationCreatedEvent;
import com.parkmate.parkingservice.parkingoperation.domain.ParkingOperation;
import com.parkmate.parkingservice.parkingoperation.dto.request.*;
import com.parkmate.parkingservice.parkingoperation.dto.response.ParkingOperationResponseDto;
import com.parkmate.parkingservice.parkingoperation.dto.response.WeeklyOperationResponseDto;
import com.parkmate.parkingservice.parkingoperation.infrastructure.ParkingOperationMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ParkingOperationServiceImpl implements ParkingOperationService {

    private final ParkingOperationMongoRepository parkingOperationMongoRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public void register(ParkingOperationRegisterRequestDto parkingOperationCreateRequestDto) {

        parkingOperationMongoRepository.findByParkingLotUuidAndOperationDate(
                parkingOperationCreateRequestDto.getParkingLotUuid(),
                parkingOperationCreateRequestDto.getOperationDate()
        ).ifPresent(parkingOperation -> {
            throw new BaseException(ResponseStatus.DUPLICATE_RESOURCE);
        });

        ParkingOperation operation = parkingOperationMongoRepository.save(
                parkingOperationCreateRequestDto.toEntity()
        );

        eventPublisher.publishEvent(
                OperationCreatedEvent.from(operation)
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

    @Transactional
    @Override
    public void delete(ParkingOperationDeleteRequestDto parkingOperationDeleteRequestDto) {

        parkingOperationMongoRepository.deleteByParkingLotUuidAndParkingOperationUuid(
                parkingOperationDeleteRequestDto.getParkingLotUuid(),
                parkingOperationDeleteRequestDto.getParkingOperationUuid()
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

    @Transactional(readOnly = true)
    @Override
    public List<WeeklyOperationResponseDto> getWeeklyOperations(String parkingLotUuid) {

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(6);

        List<LocalDate> dates = startDate.datesUntil(endDate).sorted(Comparator.comparing(localDate -> localDate)).toList();

        List<ParkingOperation> operations = parkingOperationMongoRepository.findAllByParkingLotUuidAndOperationDateBetween(
                parkingLotUuid,
                startDate,
                endDate
        );

        return dates.stream().map(date -> {

            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
            int dayOfMonth = date.getDayOfMonth();

            for (ParkingOperation operation : operations) {
                if (date.equals(operation.getOperationDate().toLocalDate())) {
                    return WeeklyOperationResponseDto.from(
                            dayOfWeek,
                            dayOfMonth,
                            operation.getValidStartTime().toLocalTime(),
                            operation.getValidEndTime().toLocalTime()
                    );
                }
            }

            return WeeklyOperationResponseDto.from(dayOfWeek, dayOfMonth);
        }).toList();
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
