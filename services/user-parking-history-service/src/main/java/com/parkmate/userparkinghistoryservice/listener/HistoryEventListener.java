package com.parkmate.userparkinghistoryservice.listener;

import com.parkmate.userparkinghistoryservice.kafka.event.HistoryEvent;
import com.parkmate.userparkinghistoryservice.userpakinrghistory.infrastructure.UserParkingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class HistoryEventListener {

    private final UserParkingHistoryRepository userParkingHistoryRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleHistoryEvent(HistoryEvent event) {
        userParkingHistoryRepository.save(event.toEntity());
    }
}
