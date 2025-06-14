package com.parkmate.parkingreadservice.parkingoperation.infrastructure;

import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;
import com.parkmate.parkingreadservice.parkingoperation.domain.ParkingLotOperationRead;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CustomOperationRepositoryImpl implements CustomOperationRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void create(OperationCreateEvent operationCreateEvent) {
        mongoTemplate.save(operationCreateEvent.toEntity());
    }

    @Override
    public List<String> findOperatingUuidsByTimeRange(List<String> parkingLotUuids,
                                                      LocalDateTime date,
                                                      LocalDateTime startTime,
                                                      LocalDateTime endTime) {

        Criteria criteria = Criteria.where("parkingLotUuid").in(parkingLotUuids)
                .and("operationDate").is(date)
                .and("startDateTime").lte(startTime)
                .and("endDateTime").gte(endTime);

        Query query = new Query(criteria);

        List<ParkingLotOperationRead> results = mongoTemplate.find(query, ParkingLotOperationRead.class);

        return results.stream()
                .map(ParkingLotOperationRead::getParkingLotUuid)
                .collect(Collectors.toList());
    }

}
