package com.metaphorce.product.mapper;

import com.metaphorce.commonslib.dto.CartItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.math.BigDecimal;

@Mapper
public interface GlobalMapper {

    GlobalMapper INSTANCE = (GlobalMapper) Mappers.getMapper(GlobalMapper.class);

    CartItemDto toDto(Long productId, String productImage, String productName, BigDecimal productPrice, Integer quantity);

}
