package com.propvuebrand.fulfillment.centers.mapper;

import com.propvuebrand.fulfillment.centers.domain.dto.ProductDto;
import com.propvuebrand.fulfillment.centers.domain.entity.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper extends Mappable<Product, ProductDto> {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Override
    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDto dto);

    /**
     * Метод для обновления существующей сущности Product
     * на основе данных из ProductDto.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ProductDto dto, @MappingTarget Product entity);
}
