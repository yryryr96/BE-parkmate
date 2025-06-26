package com.parkmate.userparkinghistoryservice.userpakinrghistory.infrastructure;

import com.parkmate.userparkinghistoryservice.userpakinrghistory.domain.UserParkingHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserParkingHistoryRepository extends MongoRepository<UserParkingHistory, String> {
}
