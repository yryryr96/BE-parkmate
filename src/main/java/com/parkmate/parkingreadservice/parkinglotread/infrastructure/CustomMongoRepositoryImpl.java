package com.parkmate.parkingreadservice.parkinglotread.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.event.ParkingLotReactionsUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomMongoRepositoryImpl implements CustomMongoRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void updateParkingLotMetadata(ParkingLotMetadataUpdateEvent parkingLotMetadataUpdateEvent) {

        Query query = new Query();
        Update update = new Update();

        query.addCriteria(
                Criteria.where("parkingLotUuid")
                        .is(parkingLotMetadataUpdateEvent.getParkingLotUuid())
        );

        Map<String, Object> map = createUpdateMap(parkingLotMetadataUpdateEvent);
        map.forEach((key, value) -> {
            if(!key.equals("parkingLotUuid") && value != null) {
                update.set(key, value);
            }
        });
        update.set("updatedAt", LocalDateTime.now());

        mongoTemplate.updateFirst(query, update, ParkingLotRead.class);
    }

    @Override
    public void updateParkingLotReactions(ParkingLotReactionsUpdateEvent parkingLotReactionsUpdateEvent) {

        Query query = new Query();
        Update update = new Update();

        query.addCriteria(
                Criteria.where("parkingLotUuid")
                        .is(parkingLotReactionsUpdateEvent.getParkingLotUuid())
        );

        Map<String, Object> map = createUpdateMap(parkingLotReactionsUpdateEvent);
        map.forEach((key, value) -> {
            if(!key.equals("parkingLotUuid") && value != null) {
                update.set(key, value);
            }
        });
        update.set("updatedAt", LocalDateTime.now());

        mongoTemplate.updateFirst(query, update, ParkingLotRead.class);
    }

    private Map<String, Object> createUpdateMap(Object object) {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, new TypeReference<>() {});
    }
}
