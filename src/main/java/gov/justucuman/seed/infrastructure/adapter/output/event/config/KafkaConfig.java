package gov.justucuman.seed.infrastructure.adapter.output.event.config;

import gov.justucuman.seed.infrastructure.adapter.output.event.dto.ProductEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.topics.product-events}")
    private String productEventsTopic;

    @Bean
    public NewTopic productEventsTopic() {
        return TopicBuilder.name(productEventsTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    /**
     * This is the KafkaTemplate to send events
     */
    @Bean
    public KafkaTemplate<String, ProductEvent> kafkaTemplate(
            ProducerFactory<String, ProductEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
