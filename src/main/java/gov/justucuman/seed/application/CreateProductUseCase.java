package gov.justucuman.seed.application;

import gov.justucuman.seed.domain.model.Product;
import gov.justucuman.seed.domain.port.in.CreateProduct;
import gov.justucuman.seed.domain.port.out.ProductSavePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductUseCase implements CreateProduct {

    private final ProductSavePort productSavePort;

    @Override
    public Product perform(Product product) {
        return productSavePort.perform(product);
    }
}
