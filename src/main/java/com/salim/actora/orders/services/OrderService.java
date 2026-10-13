package com.salim.actora.orders.services;

import com.salim.actora.orders.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService {

    public List<Order> getOrderByCustomer(Long customerId);

    public Order getOrderByOrderId(Long orderId);
}
