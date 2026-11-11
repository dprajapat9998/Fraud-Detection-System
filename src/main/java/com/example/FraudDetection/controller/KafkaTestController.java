package com.example.FraudDetection.controller;

import com.example.FraudDetection.service.KafkaProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaTestController {

    private final KafkaProducerService kafkaProducerService;

    public KafkaTestController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/test")
    public String sendMessage(@RequestBody String message) {

        kafkaProducerService.sendMessage(message);

        return "Message sent to Kafka successfully!";
    }
}