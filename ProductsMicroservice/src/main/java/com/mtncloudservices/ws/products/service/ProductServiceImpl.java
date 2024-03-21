package com.mtncloudservices.ws.products.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.mtncloudservices.ws.products.rest.CreateProductRestModel;

@Service
public class ProductServiceImpl implements ProductService {

    // Kafka template for executing high-level operations
    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductRestModel productRestModel) {


        String productId = UUID.randomUUID().toString();

        // TODO: Persist product details into database before publishing event

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
                productId,
                productRestModel.getTitle(),
                productRestModel.getPrice(),
                productRestModel.getQuantity());

        // Synchronous publish event to topic
        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
                kafkaTemplate.send(
                        "product-created-events-topic",
                        productId,
                        productCreatedEvent);

        // Process returned CompletionStage or Exception
        future.whenComplete((result, exception) -> {

            if (exception != null) {
                LOGGER.error("****** Failed to send message: " + exception.getMessage());
            } else {
                LOGGER.info("****** Message send successfully: " + result.getRecordMetadata());
            }
        });

        LOGGER.info("****** Returning product ID.");

        // Block current thread until current future is complete
        // future.join();

        return productId;
    }
}
