package com.salim.actora.orders.services;

import com.salim.actora.orders.entity.Order;
import com.salim.actora.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public List<Order> getOrderByCustomer(Long customerId) {

        return orderRepository.findAllByCustomerId(customerId);
    }

    @Override
    public Order getOrderByOrderId(Long orderId) {

        return orderRepository.findById(orderId).orElseThrow(()->
                new IllegalArgumentException("No order found with id!"));
    }
}
