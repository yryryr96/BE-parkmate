package com.parkmate.parkingservice.parkingoperation.application;

import com.parkmate.parkingservice.common.exception.BaseException;
import com.parkmate.parkingservice.common.response.ResponseStatus;
import com.parkmate.parkingservice.kafka.event.OperationCreatedEvent;
import com.parkmate.parkingservice.parkingoperation.domain.ParkingOperation;
import com.parkmate.parkingservice.parkingoperation.dto.request.*;
import com.parkmate.parkingservice.parkingoperation.dto.response.AmountResponseDto;
import com.parkmate.parkingservice.parkingoperation.dto.response.ParkingOperationResponseDto;
import com.parkmate.parkingservice.parkingoperation.dto.response.WeeklyOperationResponseDto;
import com.parkmate.parkingservice.parkingoperation.infrastructure.ParkingOperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingOperationServiceImpl implements ParkingOperationService {

    private final ParkingOperationRepository parkingOperationRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public void register(ParkingOperationRegisterRequestDto parkingOperationCreateRequestDto) {

        parkingOperationRepository.findByParkingLotUuidAndOperationDate(
                parkingOperationCreateRequestDto.getParkingLotUuid(),
                parkingOperationCreateRequestDto.getOperationDate()
        ).ifPresent(parkingOperation -> {
            throw new BaseException(ResponseStatus.DUPLICATE_RESOURCE);
        });

        ParkingOperation operation = parkingOperationRepository.save(
                parkingOperationCreateRequestDto.toEntity()
        );

        eventPublisher.publishEvent(
                OperationCreatedEvent.from(operation)
        );
    }

    @Transactional
    @Override
    public void update(ParkingOperationUpdateRequestDto parkingOperationUpdateRequestDto) {

        ParkingOperation parkingOperation = parkingOperationRepository.findByParkingLotUuidAndParkingOperationUuid(
                parkingOperationUpdateRequestDto.getParkingLotUuid(),
                parkingOperationUpdateRequestDto.getParkingOperationUuid()
        ).orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        parkingOperationRepository.save(
                createUpdatedParkingOperationEntity(parkingOperation, parkingOperationUpdateRequestDto)
        );
    }

    @Transactional
    @Override
    public void delete(ParkingOperationDeleteRequestDto parkingOperationDeleteRequestDto) {

        parkingOperationRepository.deleteByParkingLotUuidAndParkingOperationUuid(
                parkingOperationDeleteRequestDto.getParkingLotUuid(),
                parkingOperationDeleteRequestDto.getParkingOperationUuid()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParkingOperationResponseDto> getParkingOperationList(ParkingOperationListGetRequestDto parkingOperationListGetRequestDto) {

        return parkingOperationRepository.findAllByParkingLotUuidAndOperationDateBetween(
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
        ParkingOperation parkingOperation = parkingOperationRepository.findByParkingLotUuidAndParkingOperationUuid(
                parkingOperationGetRequestDto.getParkingLotUuid(),
                parkingOperationGetRequestDto.getParkingOperationUuid()
        ).orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        return ParkingOperationResponseDto.from(parkingOperation);
    }

    @Transactional(readOnly = true)
    @Override
    public List<WeeklyOperationResponseDto> getWeeklyOperations(String parkingLotUuid) {

        final int DEFAULT_DAY_RANGE = 7;

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(DEFAULT_DAY_RANGE);

        List<ParkingOperation> operations = parkingOperationRepository.findAllByParkingLotUuidAndOperationDateBetween(
                parkingLotUuid,
                startDate,
                endDate
        );

        return createWeeklyOperationResponses(startDate, endDate, operations);
    }

    @Override
    public ParkingOperationResponseDto getDailyOperation(String parkingLotUuid,
                                                         LocalDate date) {

        ParkingOperation operation = parkingOperationRepository.findByParkingLotUuidAndOperationDate(
                parkingLotUuid,
                date.atStartOfDay()
        ).orElseThrow(() -> new BaseException(ResponseStatus.RESOURCE_NOT_FOUND));

        return ParkingOperationResponseDto.from(operation);
    }

    @Override
    public List<String> getOpenParkingLotUuids(List<String> parkingLotUuids) {
        return parkingOperationRepository.getOpenParkingLotUuids(parkingLotUuids, LocalDateTime.now());
    }

    @Override
    public AmountResponseDto calculateTotalParkingAmount(AmountRequestDto amountRequestDto) {

        String parkingLotUuid = amountRequestDto.getParkingLotUuid();
        LocalDateTime startDateTime = amountRequestDto.getStartDateTime();
        LocalDateTime endDateTime = amountRequestDto.getEndDateTime();

        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();

        Map<LocalDate, ParkingOperation> operationInfoMap =
                parkingOperationRepository.findAllByParkingLotUuidAndOperationDateBetween(parkingLotUuid, startDate, endDate.plusDays(1))
                        .stream()
                        .collect(Collectors.toMap(o -> o.getOperationDate().toLocalDate(), Function.identity()));

        long totalAmount = 0;

        for (LocalDate currentDate = startDate; !currentDate.isAfter(endDate); currentDate = currentDate.plusDays(1)) {

            ParkingOperation currentDayInfo = operationInfoMap.get(currentDate);
            if (currentDayInfo == null || currentDayInfo.getBaseIntervalMinutes() <= 0) {
                log.warn("No valid operation found for date: {} or base interval minutes is zero", currentDate);
                continue;
            }

            LocalDateTime loopStart = currentDate.isEqual(startDate) ? startDateTime : currentDate.atStartOfDay();
            LocalDateTime loopEnd = currentDate.isEqual(endDate) ? endDateTime : currentDate.plusDays(1).atStartOfDay();

            LocalDateTime chargeableStart = loopStart.isAfter(currentDayInfo.getValidStartTime())
                    ? loopStart : currentDayInfo.getValidStartTime();
            LocalDateTime chargeableEnd = loopEnd.isBefore(currentDayInfo.getValidEndTime())
                    ? loopEnd : currentDayInfo.getValidEndTime();

            if (chargeableEnd.isAfter(chargeableStart)) {

                long minutesOnThisDay = Duration.between(chargeableStart, chargeableEnd).toMinutes();
                if (minutesOnThisDay > 0) {
                    double numberOfIntervals = Math.ceil((double) minutesOnThisDay / currentDayInfo.getBaseIntervalMinutes());
                    long dailyFee = (long) (numberOfIntervals * currentDayInfo.getBaseFee());
                    totalAmount += dailyFee;
                }
            }
        }

        return AmountResponseDto.from(totalAmount);
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

    private List<WeeklyOperationResponseDto> createWeeklyOperationResponses(LocalDate startDate,
                                                                            LocalDate endDate,
                                                                            List<ParkingOperation> operations) {

        Map<LocalDate, ParkingOperation> operationMap = operations.stream()
                .collect(Collectors.toMap(
                        op -> op.getOperationDate().toLocalDate(),
                        op -> op
                ));

        return startDate.datesUntil(endDate)
                .map(date -> {
                    String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN);
                    int dayOfMonth = date.getDayOfMonth();

                    ParkingOperation operation = operationMap.get(date);

                    if (operation != null) {
                        return WeeklyOperationResponseDto.of(
                                dayOfWeek,
                                dayOfMonth,
                                operation.getValidStartTime().toLocalTime(),
                                operation.getValidEndTime().toLocalTime()
                        );
                    } else {
                        return WeeklyOperationResponseDto.of(dayOfWeek, dayOfMonth);
                    }
                })
                .toList();
    }
}
