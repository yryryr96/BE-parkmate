package com.parkmate.parkingreadservice.parkingoperation.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkmate.parkingreadservice.kafka.event.OperationCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ParkingLotOperationMongoRepositoryImpl implements ParkingLotOperationMongoRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void create(OperationCreateEvent operationCreateEvent) {
            mongoTemplate.save(operationCreateEvent.toEntity());
    }

    private Map<String, Object> createUpdateMap(Object object) {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, new TypeReference<>() {});
    }
}
