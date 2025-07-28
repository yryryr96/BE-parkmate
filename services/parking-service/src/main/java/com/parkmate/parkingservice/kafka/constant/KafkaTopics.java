package com.parkmate.parkingservice.kafka.constant;

public class KafkaTopics {

    public static final String parkingLotCreated = "parking.parking-lot.created";
    public static final String parkingLotMetadataUpdated = "parking.parking-lot-metadata.updated";
    public static final String parkingLotReactionsUpdated = "parking.parking-lot-reactions.updated";
    public static final String parkingLotOperationCreated = "parking.parking-lot-operation.created";

    private KafkaTopics() {
    }
}