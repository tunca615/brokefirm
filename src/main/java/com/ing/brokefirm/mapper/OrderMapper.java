package com.ing.brokefirm.mapper;

import com.ing.brokefirm.controller.OrderResource.OrderResource;
import com.ing.brokefirm.controller.dto.OrderDto;
import com.ing.brokefirm.model.Order;
import com.ing.brokefirm.repository.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper( OrderMapper.class );

    Order orderDtoToOrder(OrderDto orderDto);

    OrderResource orderToOrderResource(Order order);

    OrderEntity orderToOrderEntity(Order order);

    Order orderEntityToOrder(OrderEntity orderEntity);
}
