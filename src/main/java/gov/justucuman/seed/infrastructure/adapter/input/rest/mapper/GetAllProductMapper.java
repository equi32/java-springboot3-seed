package gov.justucuman.seed.infrastructure.adapter.input.rest.mapper;

import gov.justucuman.seed.domain.model.Product;
import gov.justucuman.seed.infrastructure.adapter.input.rest.dto.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GetAllProductMapper {
    GetAllProductMapper INSTANCE = Mappers.getMapper(GetAllProductMapper.class);

    List<ProductResponse> toResponse(List<Product> product);
}
