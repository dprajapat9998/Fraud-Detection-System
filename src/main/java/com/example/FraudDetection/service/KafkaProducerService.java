package com.example.FraudDetection.service;

import com.example.FraudDetection.dto.TransactionResponseDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "transaction-events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducerService(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper) {

        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(String message) {

        kafkaTemplate.send(TOPIC, message)
                .whenComplete((result, exception) -> {

                    if (exception != null) {
                        System.err.println(
                                "Failed to send Kafka message: "
                                        + exception.getMessage());
                    } else {
                        System.out.println(
                                "Message sent to topic: " + TOPIC
                                        + ", partition: "
                                        + result.getRecordMetadata().partition()
                                        + ", offset: "
                                        + result.getRecordMetadata().offset());
                    }
                });
    }

    public void sendTransactionEvent(TransactionResponseDTO transaction) {

        try {
            String jsonMessage = objectMapper.writeValueAsString(transaction);

            sendMessage(jsonMessage);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to convert transaction into JSON", e);
        }
    }
}