package com.salim.actora.orders.controller;

import com.salim.actora.orders.dto.OrderResponseDto;
import com.salim.actora.orders.services.OrderService;
import com.salim.actora.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/byCustomer")
    public ResponseEntity<?> getOrderByCustomer(@RequestParam Long customerId){

        var body = orderService.getOrderByCustomer(customerId);

        return ResponseBuilder.build(body,null, HttpStatus.OK, LocalDateTime.now());
    }

}
