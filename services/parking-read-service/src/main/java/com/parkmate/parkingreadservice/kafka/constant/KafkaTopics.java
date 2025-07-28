package com.parkmate.parkingreadservice.kafka.constant;

public class KafkaTopics {

    public static final String PARKING_LOT_CREATED = "parking.parking-lot.created";
    public static final String PARKING_LOT_METADATA_UPDATED = "parking.parking-lot-metadata.updated";
    public static final String PARKING_LOT_REACTIONS_UPDATED = "parking.parking-lot-reactions.updated";
    public static final String PARKING_LOT_OPERATION_CREATED = "parking.parking-lot-operation.created";
    public static final String RESERVATION = "reservation.reservation";
    public static final String REVIEW_SUMMARY_UPDATED = "batch.review-summary.updated";

    private KafkaTopics() {}
}
