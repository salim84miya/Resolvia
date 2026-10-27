package com.salim.actora.orders.services;

import com.salim.actora.orders.data.OrderStatus;
import com.salim.actora.orders.dto.OrderResponseDto;
import com.salim.actora.orders.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {

    public List<OrderResponseDto> getOrderByCustomer(Long customerId);

    public OrderResponseDto getOrderByOrderId(Long orderId);

    public OrderStatus getOrderStatus(Long orderId);

//    public OrderResponseDto createOrder();
}
