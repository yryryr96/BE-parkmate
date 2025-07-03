package com.parkmate.parkingreadservice.parkinglotread.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoBulkWriteException;
import com.parkmate.parkingreadservice.kafka.event.*;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomParkingLotRepositoryImpl implements CustomParkingLotRepository {

    private final MongoTemplate mongoTemplate;

    private static final String PARKING_LOT_UUID_FIELD = "parkingLotUuid";

    @Override
    public void create(ParkingLotCreateEvent parkingLotCreateEvent) {

        Query query = new Query();
        Update update = new Update();

        query.addCriteria(
                Criteria.where("parkingLotUuid")
                        .is(parkingLotCreateEvent.getParkingLotUuid())
        );

        Map<String, Object> map = createUpdateMap(parkingLotCreateEvent);
        map.forEach((key, value) -> {
            if(!key.equals("parkingLotUuid") && value != null) {
                update.set(key, value);
            }
        });

        mongoTemplate.upsert(query, update, ParkingLotRead.class);
    }

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
    public void updateParkingLotReactions(List<ParkingLotReactionsUpdateEvent> parkingLotReactionsUpdateEvents) {

        if (parkingLotReactionsUpdateEvents == null || parkingLotReactionsUpdateEvents.isEmpty()) {
            return;
        }

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED,
                ParkingLotRead.class);

        final Map<ReactionType, String> reactionFieldMap = Map.of(
                ReactionType.LIKE, "likeCount",
                ReactionType.DISLIKE, "dislikeCount"
        );

        for (ParkingLotReactionsUpdateEvent event : parkingLotReactionsUpdateEvents) {

            Query query = new Query();
            query.addCriteria(Criteria.where("parkingLotUuid").is(event.getParkingLotUuid()));

            Update update = new Update();

            ReactionType currentReaction = event.getReactionType();
            ReactionType previousReaction = event.getPreviousReactionType();

            String fieldToDecrement = reactionFieldMap.get(previousReaction);
            if (fieldToDecrement != null) {
                update.inc(fieldToDecrement, -1);
            }

            String fieldToIncrement = reactionFieldMap.get(currentReaction);
            if (fieldToIncrement != null) {
                update.inc(fieldToIncrement, 1);
            }

            if (update.getUpdateObject().isEmpty()) {
                continue;
            }

            update.set("updatedAt", event.getTimestamp());
            bulkOperations.updateOne(query, update);
        }

        try {
            bulkOperations.execute();
        } catch (MongoBulkWriteException e) {
            e.getWriteErrors().forEach(error -> {
                int failedIndex = error.getIndex();
                String errorMessage = error.getMessage();
                log.error("Failed at index {}: {}", failedIndex, errorMessage);
            });
        }
    }

    @Override
    public List<ParkingLotRead> findByParkingLotUuids(List<String> parkingLotUuids) {

        Query query = new Query();
        query.addCriteria(Criteria.where("parkingLotUuid").in(parkingLotUuids));
        return mongoTemplate.find(query, ParkingLotRead.class);
    }

    @Override
    public void bulkUpdateRating(List<ReviewSummaryUpdateEvent> events) {

        if (events == null || events.isEmpty()) {
            return;
        }

        BulkOperations bulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED,
                ParkingLotRead.class);

        for (ReviewSummaryUpdateEvent event : events) {

            Query query = new Query();
            query.addCriteria(Criteria.where(PARKING_LOT_UUID_FIELD).is(event.getParkingLotUuid()));

            Update update = new Update();
            update.set("rating", event.getAverageRating());
            bulkOperations.updateOne(query, update);
        }

        try {
            bulkOperations.execute();
        } catch (MongoBulkWriteException e) {
            e.getWriteErrors().forEach(error -> {
                int failedIndex = error.getIndex();
                String errorMessage = error.getMessage();
                log.error("Failed at index {}: {}", failedIndex, errorMessage);
            });
        }
    }

    @Override
    public List<ParkingLotRead> search(String keyword) {

        Document searchStage = new Document("$search",
                new Document("index", "name")
                        .append("text", new Document()
                                .append("query", keyword)
                                .append("path", "name")
                        )
        );

        AggregationOperation searchOperation = context -> searchStage;

        Aggregation aggregation = Aggregation.newAggregation(searchOperation);

        AggregationResults<ParkingLotRead> results = mongoTemplate.aggregate(
                aggregation,
                "parking_lot_read",
                ParkingLotRead.class
        );

        return results.getMappedResults();
    }

    private Map<String, Object> createUpdateMap(Object object) {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, new TypeReference<>() {});
    }
}
