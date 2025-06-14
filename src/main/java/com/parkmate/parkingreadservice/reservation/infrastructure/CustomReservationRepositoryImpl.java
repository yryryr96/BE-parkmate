package com.parkmate.parkingreadservice.reservation.infrastructure;

import com.parkmate.parkingreadservice.reservation.dto.response.ReserveParkingLotResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomReservationRepositoryImpl implements CustomReservationRepository {

    private final MongoTemplate mongoTemplate;
    private final MongoConverter mongoConverter;

    @Override
    public List<ReserveParkingLotResponseDto> findAllByParkingLotUuidsAndDates(List<String> parkingLotUuids,
                                                                               LocalDateTime startDateTime,
                                                                               LocalDateTime endDateTime) {

        Criteria criteria = Criteria.where("parkingLotUuid").in(parkingLotUuids)
                .and("startDateTime").lt(endDateTime)
                .and("endDateTime").gt(startDateTime);

        MatchOperation matchOperation = Aggregation.match(criteria);

        GroupOperation groupOperation = Aggregation.group("parkingLotUuid")
                .count().as("reservedSpotCount");

        ProjectionOperation projectionOperation = Aggregation.project("reservedSpotCount")
                .and("_id").as("parkingLotUuid")
                .andExclude("_id");

        Aggregation aggregation = Aggregation.newAggregation(
                matchOperation,
                groupOperation,
                projectionOperation
        );

        AggregationResults<ReserveParkingLotResponseDto> results = mongoTemplate.aggregate(
                aggregation, "reservation_records", ReserveParkingLotResponseDto.class
        );

        return results.getMappedResults();
    }
}
