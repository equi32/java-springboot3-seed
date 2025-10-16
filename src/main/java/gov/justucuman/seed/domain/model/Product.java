package gov.justucuman.seed.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Product(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        ProductStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
