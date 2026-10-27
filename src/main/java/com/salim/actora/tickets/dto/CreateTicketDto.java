package com.salim.actora.tickets.dto;

import com.salim.actora.tickets.data.TicketPriority;
import com.salim.actora.tickets.data.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketDto {

    @NotBlank(message = "order id is needed for creating ticket")
    private Long orderId;

    @NotBlank(message = "customer id is needed for creating ticket")
    private Long customerId;

    @NotBlank(message = "title is needed for creating ticket")
    private String title;

    @NotBlank(message = "description is needed for creating ticket")
    private String description;

    @NotBlank(message = "ticket priority is needed for creating ticket")
    private TicketPriority priority;
}
