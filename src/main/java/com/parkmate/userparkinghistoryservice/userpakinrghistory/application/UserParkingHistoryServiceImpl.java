package com.parkmate.userparkinghistoryservice.userpakinrghistory.application;

import com.parkmate.userparkinghistoryservice.common.exception.BaseException;
import com.parkmate.userparkinghistoryservice.common.response.ResponseStatus;
import com.parkmate.userparkinghistoryservice.feign.ReservationClient;
import com.parkmate.userparkinghistoryservice.feign.response.ReservationResponse;
import com.parkmate.userparkinghistoryservice.kafka.event.EventType;
import com.parkmate.userparkinghistoryservice.kafka.event.HistoryEvent;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.dto.request.EntryRequestDto;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.dto.request.ExitRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserParkingHistoryServiceImpl implements UserParkingHistoryService {

    private final ReservationClient reservationClient;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public boolean requestEntry(EntryRequestDto entryRequestDto) {

        String userUuid = entryRequestDto.getUserUuid();
        String parkingLotUuid = entryRequestDto.getParkingLotUuid();
        String reservationCode = entryRequestDto.getReservationCode();

        ReservationResponse reservation = reservationClient.getReservation(userUuid, reservationCode, parkingLotUuid).getData();
        Optional.ofNullable(reservation).ifPresentOrElse(
                r -> {
                    if (!r.canEnter(entryRequestDto)) {
                        throw new BaseException(ResponseStatus.INVALID_ENTRY_REQUEST);
                    }
                    HistoryEvent event = entryRequestDto.toEvent(r.getParkingSpotName(), EventType.CREATED);
                    eventPublisher.publishEvent(event);
                },
                () -> {
                    throw new BaseException(ResponseStatus.INVALID_ENTRY_REQUEST);
                }
        );

        return true;
    }

    @Transactional
    @Override
    public boolean requestExit(ExitRequestDto exitRequestDto) {

        String userUuid = exitRequestDto.getUserUuid();
        String parkingLotUuid = exitRequestDto.getParkingLotUuid();
        String reservationCode = exitRequestDto.getReservationCode();

        ReservationResponse reservation = reservationClient.getReservation(userUuid, reservationCode, parkingLotUuid).getData();
        Optional.ofNullable(reservation).ifPresentOrElse(
                r -> {
                    if (!r.canExit(exitRequestDto)) {
                        throw new BaseException(ResponseStatus.INVALID_EXIT_REQUEST);
                    }
                    HistoryEvent event = exitRequestDto.toEvent(r.getParkingSpotName(), EventType.CREATED);
                    eventPublisher.publishEvent(event);
                },
                () -> {
                    throw new BaseException(ResponseStatus.INVALID_EXIT_REQUEST);
                }
        );

        return true;
    }
}
