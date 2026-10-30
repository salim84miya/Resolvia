package com.salim.actora.orders.dto;

import com.salim.actora.orders.data.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private Long orderId;

    private OrderStatus currentStatus;

    private LocalDateTime deliveryTime;

    private LocalDateTime createdAt;
}
