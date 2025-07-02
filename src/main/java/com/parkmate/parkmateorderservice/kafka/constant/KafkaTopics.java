package com.parkmate.parkmateorderservice.kafka.constant;

public class KafkaTopics {

    // produce
    public static final String ORDER_TOPIC = "order.order";

    // consume
    public static final String PAYMENT_TOPIC = "payment.payment";

    private KafkaTopics() {}
}
