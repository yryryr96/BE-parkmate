package com.parkmate.parkingservice.parkingoperation.infrastructure;

import com.parkmate.parkingservice.parkingoperation.domain.ParkingOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomOperationRepositoryImpl implements CustomOperationRepository {

    private final MongoTemplate mongoTemplate;

    private static final String PARKING_LOT_UUID = "parkingLotUuid";
    private static final String OPERATION_DATE = "operationDate";
    private static final String START_DATE_TIME = "validStartTime";
    private static final String END_DATE_TIME = "validEndTime";
    private static final String COLLECTION_NAME = "parking_operations";

    @Override
    public List<String> getOpenParkingLotUuids(List<String> parkingLotUuids,
                                               LocalDateTime now) {

        Query query = new Query();
        query.addCriteria(Criteria.where(PARKING_LOT_UUID).in(parkingLotUuids)
                .and(OPERATION_DATE).is(now.toLocalDate().atStartOfDay())
                .and(START_DATE_TIME).lte(now)
                .and(END_DATE_TIME).gte(now));

        query.with(Sort.by(Sort.Direction.DESC, PARKING_LOT_UUID));

        List<ParkingOperation> operations = mongoTemplate.find(query, ParkingOperation.class, COLLECTION_NAME);
        return operations.stream()
                .map(ParkingOperation::getParkingLotUuid)
                .distinct()
                .toList();
    }
}
