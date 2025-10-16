package gov.justucuman.seed.infrastructure.adapter.output.persistence.jdbc;

import gov.justucuman.seed.common.util.DateUtils;
import gov.justucuman.seed.domain.model.Product;
import gov.justucuman.seed.domain.model.ProductStatus;
import gov.justucuman.seed.domain.port.out.ProductFindByIdPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductFindByIdJdbcAdapter implements ProductFindByIdPort {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_BY_ID_SQL =
            "SELECT * FROM products WHERE id = ?";

    @Override
    public Optional<Product> perform(UUID id) {
        log.info("Searching product with id {}", id);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, productRowMapper(), id));
        } catch (EmptyResultDataAccessException ex) {
            log.info("Product with id {} was not found: {}", id, ex.getMessage());
            return Optional.empty();
        }
    }

    /**
     * RowMapper to convert ResultSet to a Product
     * @return RowMapper<Product>
     */
    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> new Product(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getInt("stock"),
                ProductStatus.fromName(rs.getString("status")),
                DateUtils.toLocalDateTime(rs.getTimestamp("created_at")),
                DateUtils.toLocalDateTime(rs.getTimestamp("updated_at"))
        );
    }
}
