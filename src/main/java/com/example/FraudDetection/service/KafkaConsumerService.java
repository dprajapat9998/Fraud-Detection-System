
package com.example.FraudDetection.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(
        topics = "transaction-events",
        groupId = "fraud-detection-group"
    )
    public void consumeMessage(String message) {

        System.out.println("Message received from Kafka:");
        System.out.println(message);
    }
}
