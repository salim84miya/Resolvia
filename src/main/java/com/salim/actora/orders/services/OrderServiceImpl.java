package com.salim.actora.orders.services;

import com.salim.actora.orders.data.OrderStatus;
import com.salim.actora.orders.dto.OrderResponseDto;
import com.salim.actora.orders.entity.Order;
import com.salim.actora.orders.repository.OrderRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Tool(description = "Returns the status of the order for the provided order Id in long.")
    public OrderStatus getOrderStatus(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("No order found with id"));

        return order.getOrderStatus();
    }

    @Override
    @Tool(description = "gives list of all the orders of customers by customer id.Customer Id is Long")
    public List<OrderResponseDto> getOrderByCustomer(Long customerId) {


        Optional<List<Order>> orders = orderRepository.findAllByCustomerId(customerId);

        return orders.map(orderList -> orderList.stream()
                .map(order ->
                        OrderResponseDto.builder()
                                .orderId(order.getId())
                                .orderStatus(order.getOrderStatus())
                                .estimatedDeliveryTime(order.getEstimatedDeliveryTime())
                                .createdAt(order.getCreatedAt())
                                .build()
                ).collect(Collectors.toList())).orElseGet(List::of);

    }

    @Override
    @Tool(description = "Gives order information for the provided order Id.Order id is in Long")
    public OrderResponseDto getOrderByOrderId(Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("No order found with id!"));

        return OrderResponseDto.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .estimatedDeliveryTime(order.getEstimatedDeliveryTime())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
