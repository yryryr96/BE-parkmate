package com.parkmate.parkingreadservice.parkingoperation.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;
import com.parkmate.parkingreadservice.parkingoperation.domain.ParkingLotOperationRead;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ParkingLotOperationMongoRepositoryImpl implements ParkingLotOperationMongoRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void create(OperationCreateEvent operationCreateEvent) {
        mongoTemplate.save(operationCreateEvent.toEntity());
    }

    @Override
    public List<ParkingLotOperationRead> findAvailableParkingLotUuids(List<String> parkingLotUuids,
                                                                      List<LocalDate> dates) {

        Query query = new Query();
        query.addCriteria(
                Criteria.where("parkingLotUuid")
                        .in(parkingLotUuids)
                        .and("operationDate")
                        .in(dates)
        );

        return mongoTemplate.find(query, ParkingLotOperationRead.class);
    }

    private Map<String, Object> createUpdateMap(Object object) {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, new TypeReference<>() {
        });
    }
}
