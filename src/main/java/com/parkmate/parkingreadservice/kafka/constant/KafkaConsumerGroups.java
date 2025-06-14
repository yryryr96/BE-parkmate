package com.parkmate.parkingreadservice.kafka.constant;

public class KafkaConsumerGroups {

    public static final String parkingLotCreatedGroup = "parking-lot-created-group";
    public static final String parkingLotMetadataUpdatedGroup = "parking-lot-metadata-updated-group";
    public static final String parkingLotReactionsUpdatedGroup = "parking-lot-reactions-updated-group";
    public static final String parkingLotOperationCreatedGroup = "parking-lot-operation-created-group";
    public static final String reservationCreatedGroup = "reservation-created-group";

    private KafkaConsumerGroups() {
    }
}
