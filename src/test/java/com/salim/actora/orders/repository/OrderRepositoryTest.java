package com.salim.actora.orders.repository;

import com.salim.actora.orders.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;


    @Test
    public void getCustomerOrderByIdSuccessfully(){
        Long orderId = 1L;
        Order obtainedOrder = orderRepository.findById(orderId)
                .orElseThrow(()->new IllegalArgumentException("No order Found!"));

        assertEquals(orderId,obtainedOrder.getId());
    }

    @Test
    public void getAllOrderByCustomerIdSuccessfully(){

        Long customerId = 101L;

        List<Order> customerOrderList = orderRepository.findAllByCustomerId(customerId);

        assertEquals(2, customerOrderList.size());

        System.out.println(customerOrderList);
    }
}
