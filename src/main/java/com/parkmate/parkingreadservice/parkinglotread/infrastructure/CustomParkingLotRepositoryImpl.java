package com.parkmate.parkingreadservice.parkinglotread.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoBulkWriteException;
import com.parkmate.parkingreadservice.common.response.CursorPage;
import com.parkmate.parkingreadservice.kafka.event.*;
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
            }
        }

        // 1. $search: 키워드로 주차장 이름 검색 (기존과 동일)
        Document searchStageDoc = new Document("$search",
                new Document("index", "name") // Atlas Search 인덱스 이름
                        .append("text", new Document()
                                .append("query", keyword)
                                .append("path", "name") // 검색할 필드
                        )
        );

        AggregationOperation searchOperation = context -> searchStageDoc;
        // 2. $addFields: 검색 결과에 'score'(정확도 점수) 필드 추가
        Document addFieldsDoc = new Document("$addFields",
                new Document("score", new Document("$meta", "searchScore"))
        );
        AggregationOperation addFieldsOperation = context -> addFieldsDoc;

        // $match 단계
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

        // $sort, $limit 단계
        AggregationOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "score", "_id");
        AggregationOperation limitOperation = Aggregation.limit(size + 1);

        Aggregation aggregation = Aggregation.newAggregation(
                searchOperation, addFieldsOperation, sortOperation, matchOperation, limitOperation
        );

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
