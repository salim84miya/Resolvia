package com.salim.actora.orders.repository;

import com.salim.actora.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    public List<Order> findAllByCustomerId(Long customerId);
}
