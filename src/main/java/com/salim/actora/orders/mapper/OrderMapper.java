package com.salim.actora.orders.mapper;

import com.salim.actora.orders.dto.OrderResponseDto;
import com.salim.actora.orders.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper mapper = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "orderId" , source = "id")
    @Mapping(target = "currentStatus" , source = "orderStatus")
    @Mapping(target = "deliveryTime" , source = "estimatedDeliveryTime")
    OrderResponseDto mapOrderToOrderDto(Order order);
}
