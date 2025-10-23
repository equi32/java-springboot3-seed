package gov.justucuman.seed.infrastructure.adapter.output.event;

import gov.justucuman.seed.domain.model.Product;
import gov.justucuman.seed.domain.port.out.ProductEventPublisherPort;
import gov.justucuman.seed.infrastructure.adapter.output.event.dto.ProductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductKafkaPublisherAdapter implements ProductEventPublisherPort {

    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;
    private final NewTopic productEventsTopic;

    @Override
    public void perform(Product product) {
        log.info("Publishing product created event for product id: {}", product.id());
        kafkaTemplate.send(productEventsTopic.name(), buildEvent(product));
    }

    private ProductEvent buildEvent(Product product) {
        return new ProductEvent(
                "PRODUCT_CREATED",
                product.id(),
                product.name(),
                product.price(),
                product.stock(),
                LocalDateTime.now()
        );
    }
}
