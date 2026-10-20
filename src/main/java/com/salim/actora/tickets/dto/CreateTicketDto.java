package com.salim.actora.tickets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketDto {

    private Long orderId;

    private Long customerId;

    private String title;

    private String description;
}
