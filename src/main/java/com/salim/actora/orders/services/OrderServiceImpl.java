package com.salim.actora.orders.services;

import com.salim.actora.orders.entity.Order;
import com.salim.actora.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Tool(description = "gives list of all the orders of customers by customer id.Customer Id is Long")
    public List<Order> getOrderByCustomer(Long customerId) {

        return orderRepository.findAllByCustomerId(customerId);
    }

    @Override
    @Tool(description = "Gives order information for the provided order Id.Order id is in Long")
    public Order getOrderByOrderId(Long orderId) {

        return orderRepository.findById(orderId).orElseThrow(()->
                new IllegalArgumentException("No order found with id!"));
    }
}
