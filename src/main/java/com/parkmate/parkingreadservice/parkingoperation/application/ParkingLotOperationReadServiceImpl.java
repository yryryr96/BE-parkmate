package com.parkmate.parkingreadservice.parkingoperation.application;

import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;
import com.parkmate.parkingreadservice.parkingoperation.domain.ParkingLotOperationRead;
import com.parkmate.parkingreadservice.parkingoperation.dto.response.ParkingLotOperationResponseDto;
import com.parkmate.parkingreadservice.parkingoperation.infrastructure.ParkingLotOperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingLotOperationReadServiceImpl implements ParkingLotOperationReadService {

    private final ParkingLotOperationRepository parkingLotOperationRepository;

    @Async
    @Override
    public void create(OperationCreateEvent operationCreateEvent) {
        parkingLotOperationRepository.save(operationCreateEvent.toEntity());
    }

    @Override
    public Set<String> getOperationsByUuidAndDateRange(List<String> parkingLotUuids,
                                                       LocalDateTime requestStart,
                                                       LocalDateTime requestEnd) {

        LocalDate requestStartDate = requestStart.toLocalDate();
        LocalDate requestEndDate = requestEnd.toLocalDate();
        List<LocalDate> dates = requestStartDate.datesUntil(requestEndDate.plusDays(1)).toList();
        List<ParkingLotOperationRead> operations = parkingLotOperationRepository.findAllByUuidAndOperationDateBetween(
                parkingLotUuids,
                dates
        );

        /**
         * map에 parkingLotUuid를 key, value로 ParkingLotOperationRead 리스트를 저장합니다.
         * 각 리스트는 operationDate 기준으로 정렬됩니다.
         */
        Map<String, List<ParkingLotOperationRead>> operationMap = operations.stream()
                .collect(Collectors.groupingBy(ParkingLotOperationRead::getParkingLotUuid,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.stream()
                                        .sorted(Comparator.comparing(ParkingLotOperationRead::getOperationDate))
                                        .collect(Collectors.toList())
                        )));

        Set<String> result = new HashSet<>();
        for (List<ParkingLotOperationRead> value : operationMap.values()) {
            if (isEntirePeriodValid(value, requestStart, requestEnd)) {

                value.stream()
                        .map(ParkingLotOperationRead::getParkingLotUuid)
                        .forEach(result::add);
            }
        }

        return result;
    }

    private boolean isEntirePeriodValid(List<ParkingLotOperationRead> dailyOperations,
                                        LocalDateTime requestStart,
                                        LocalDateTime requestEnd) {

        // 요청 시작과 종료 시간이 유효한지 확인
        if (requestStart.isAfter(requestEnd)) {
            return false;
        }

        // 1. [필수] 요청 기간 내 모든 날짜의 운영 정보가 DB에 있는지 확인
        long expectedDays = ChronoUnit.DAYS.between(requestStart.toLocalDate(), requestEnd.toLocalDate()) + 1;
        if (dailyOperations.size() != expectedDays) {
            return false;
        }

        // 2. 예약 기간이 하루인 경우 (시작일과 종료일이 같음)
        if (dailyOperations.size() == 1) {
            ParkingLotOperationRead op = dailyOperations.get(0);
            // 요청 시작 시간이 운영 시작 시간보다 전이 아니고,
            // 요청 종료 시간이 운영 종료 시간보다 후가 아니면 통과.
            return (op.getStartDateTime().isBefore(requestStart) || op.getStartDateTime().equals(requestStart)) &&
                   (op.getEndDateTime().isAfter(requestEnd) || op.getEndDateTime().equals(requestEnd));
        }
        // 3. 예약 기간이 여러 날인 경우
        else {
            // 첫날 검증
            ParkingLotOperationRead firstDayOp = dailyOperations.get(0);
            LocalDateTime firstDayLimit = LocalDateTime.of(firstDayOp.getEndDateTime().toLocalDate(), LocalTime.of(23, 59, 59));
            // 요청 시작 시간이 첫날의 운영 시간 내에 포함되는지 확인
            if (requestStart.isBefore(firstDayOp.getStartDateTime()) || firstDayOp.getEndDateTime().isBefore(firstDayLimit)) {
                return false;
            }

            // 마지막 날 검증
            ParkingLotOperationRead lastDayOp = dailyOperations.get(dailyOperations.size() - 1);
            // 요청 종료 시간이 마지막 날의 운영 시간 내에 포함되는지 확인
            if (!lastDayOp.getStartDateTime().equals(lastDayOp.getEndDateTime().toLocalDate().atStartOfDay()) ||
                requestEnd.isAfter(lastDayOp.getEndDateTime())) {
                log.info("마지막 날 검증 실패");
                log.info("요청 종료 시간: {}, 마지막 날 운영 시작 시간: {}, 마지막 날 운영 종료 시간: {}",
                        requestEnd, lastDayOp.getStartDateTime(), lastDayOp.getEndDateTime());
                return false;
            }

            // 중간 날짜들 검증 (24시간 운영이어야 함)
            for (int i = 1; i < dailyOperations.size() - 1; i++) {
                ParkingLotOperationRead intermediateDayOp = dailyOperations.get(i);
                LocalTime openTime = intermediateDayOp.getStartDateTime().toLocalTime();
                LocalTime closeTime = intermediateDayOp.getEndDateTime().toLocalTime();

                // 24시간 운영(00:00 ~ 23:59:59)이 아니면 실패
                if (!openTime.equals(LocalTime.MIN) || !closeTime.equals(LocalTime.MAX)) {
                    return false;
                }
            }
        }

        // 위의 모든 검증을 통과하면 최종 성공
        return true;
    }
}
