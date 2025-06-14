package com.parkmate.parkingreadservice.reservation.infrastructure;

import com.parkmate.parkingreadservice.reservation.domain.ReservationRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationRecordRepository extends MongoRepository<ReservationRecord, String> {
}
