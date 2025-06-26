package com.parkmate.parkingservice.parkingoperation.infrastructure;

import lombok.RequiredArgsConstructor;
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

    @Override
    public List<String> getOpenParkingLotUuids(List<String> parkingLotUuids,
                                               LocalDateTime now) {

        final String PARKING_LOT_UUID = "parkingLotUuid";
        final String OPERATION_DATE = "operationDate";
        final String START_DATE_TIME = "startDateTime";
        final String END_DATE_TIME = "endDateTime";

        Query query = new Query();
        query.addCriteria(Criteria.where(PARKING_LOT_UUID).in(parkingLotUuids)
                .and(OPERATION_DATE).is(now.toLocalDate().atStartOfDay())
                .and(START_DATE_TIME).lte(now)
                .and(END_DATE_TIME).gte(now));

        query.fields().include(PARKING_LOT_UUID);

        return mongoTemplate.find(query, String.class, PARKING_LOT_UUID);
    }
}
