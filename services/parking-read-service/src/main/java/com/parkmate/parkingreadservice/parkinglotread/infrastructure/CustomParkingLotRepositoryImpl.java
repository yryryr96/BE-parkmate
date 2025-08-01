package com.parkmate.parkingreadservice.parkinglotread.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoBulkWriteException;
import com.parkmate.parkingreadservice.common.exception.BaseException;
import com.parkmate.parkingreadservice.common.exception.ResponseStatus;
import com.parkmate.parkingreadservice.common.response.CursorPage;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotCreateEvent;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotMetadataUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ParkingLotReactionsUpdateEvent;
import com.parkmate.parkingreadservice.kafka.event.parkinglot.ReactionType;
import com.parkmate.parkingreadservice.kafka.event.review.ReviewSummaryUpdateEvent;
import com.parkmate.parkingreadservice.parkinglotread.domain.ParkingLotRead;
import com.parkmate.parkingreadservice.parkinglotread.dto.request.ParkingLotSearchRequestDto;
import com.parkmate.parkingreadservice.parkinglotread.dto.response.ParkingLotSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Base64;
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
                Criteria.where(PARKING_LOT_UUID_FIELD)
                        .is(parkingLotCreateEvent.getParkingLotUuid())
        );

        Map<String, Object> map = createUpdateMap(parkingLotCreateEvent);
        map.forEach((key, value) -> {
            if(!key.equals(PARKING_LOT_UUID_FIELD) && value != null) {
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
                Criteria.where(PARKING_LOT_UUID_FIELD)
                        .is(parkingLotMetadataUpdateEvent.getParkingLotUuid())
        );

        Map<String, Object> map = createUpdateMap(parkingLotMetadataUpdateEvent);
        map.forEach((key, value) -> {
            if(!key.equals(PARKING_LOT_UUID_FIELD) && value != null) {
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
            query.addCriteria(Criteria.where(PARKING_LOT_UUID_FIELD).is(event.getParkingLotUuid()));

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
        query.addCriteria(Criteria.where(PARKING_LOT_UUID_FIELD).in(parkingLotUuids));
        return mongoTemplate.find(query, ParkingLotRead.class);
    }

    @Override
    public void bulkUpdateReviewInfo(List<ReviewSummaryUpdateEvent> events) {

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
            update.set("reviewCount", event.getTotalReview());
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
    public CursorPage<ParkingLotSearchDto> search(ParkingLotSearchRequestDto request) {

        final int PAGE_DEFAULT_SIZE = 10;

        String keyword = request.getKeyword();
        int size = request.getSize() == null ? PAGE_DEFAULT_SIZE : request.getSize();
        String cursor = request.getCursor();

        Double lastScore = null;
        String lastId = null;

        if (cursor != null && !cursor.isEmpty()) {
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(cursor);
                String[] parts = new String(decodedBytes).split("_");
                lastScore = Double.parseDouble(parts[0]);
                lastId = parts[1];
            } catch (Exception e) {
                log.error("Cursor decoding failed: {}", e.getMessage());
                throw new BaseException(ResponseStatus.INVALID_SEARCH_CURSOR);
            }
        }

        // mongoDB Aggregation Pipeline
        Document searchStageDoc = new Document("$search",
                new Document("index", "name")
                        .append("text", new Document()
                                .append("query", keyword)
                                .append("path", "name")
                        )
        );

        AggregationOperation searchOperation = context -> searchStageDoc;

        Document addFieldsDoc = new Document("$addFields",
                new Document("score", new Document("$meta", "searchScore"))
        );
        AggregationOperation addFieldsOperation = context -> addFieldsDoc;

        Criteria criteria = new Criteria();
        if (lastScore != null && lastId != null) {
            criteria.orOperator(
                    Criteria.where("score").lt(lastScore),
                    new Criteria().andOperator(
                            Criteria.where("score").is(lastScore),
                            Criteria.where("_id").lt(lastId)
                    )
            );
        }
        AggregationOperation matchOperation = Aggregation.match(criteria);

        AggregationOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "score", "_id");
        AggregationOperation limitOperation = Aggregation.limit(size + 1);

        Aggregation aggregation = Aggregation.newAggregation(
                searchOperation, addFieldsOperation, sortOperation, matchOperation, limitOperation
        );

        // Cursor Pagination
        List<ParkingLotSearchDto> results = mongoTemplate.aggregate(aggregation, "parking_lot_read", ParkingLotSearchDto.class).getMappedResults();

        boolean hasNext = results.size() > size;
        String nextCursor = null;
        List<ParkingLotSearchDto> responseList = results;

        if (hasNext) {
            ParkingLotSearchDto cursorItem = results.get(size - 1);
            String rawCursor = cursorItem.getScore() + "_" + cursorItem.getId();
            nextCursor = Base64.getEncoder().encodeToString(rawCursor.getBytes());
            responseList = results.subList(0, size);
        }

        return CursorPage.<ParkingLotSearchDto>builder()
                .content(responseList)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .build();
    }

    private Map<String, Object> createUpdateMap(Object object) {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, new TypeReference<>() {});
    }
}
