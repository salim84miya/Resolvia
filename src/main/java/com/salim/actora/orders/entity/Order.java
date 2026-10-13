package com.salim.actora.orders.entity;

import com.salim.actora.orders.data.OrderStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(name="order_status")
    private OrderStatus orderStatus;

    @Column(name = "estimated_delivery_time",nullable = true)
    private LocalDateTime estimatedDeliveryTime;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
