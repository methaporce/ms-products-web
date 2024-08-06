package com.metaphorce.product.mapper;

import com.metaphorce.commonslib.dto.CartItemDto;
import com.metaphorce.commonslib.dto.OrderDto;
import com.metaphorce.commonslib.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface GlobalMapper {

    GlobalMapper INSTANCE = (GlobalMapper) Mappers.getMapper(GlobalMapper.class);

    CartItemDto toCartItemDto(Long productId, String productImage, String productName, BigDecimal productPrice, Integer quantity);

    ProductDto toProductDto(Long productId, String productImage, String productName, BigDecimal productPrice, Integer quantity);
}
