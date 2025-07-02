package com.parkmate.parkingreadservice.kafka.constant;

public class KafkaConsumerGroups {

    public static final String PARKING_LOT_CREATED_GROUP = "parking-lot-created-group";
    public static final String PARKING_LOT_METADATA_UPDATED_GROUP = "parking-lot-metadata-updated-group";
    public static final String PARKING_LOT_REACTIONS_UPDATED_GROUP = "parking-lot-reactions-updated-group";
    public static final String PARKING_LOT_OPERATION_CREATED_GROUP = "parking-lot-operation-created-group";
    public static final String RESERVATION_CREATED_GROUP = "reservation-created-group";
    public static final String REVIEW_SUMMARY_UPDATED_GROUP = "review-summary-updated-group";

    private KafkaConsumerGroups() {
    }
}
