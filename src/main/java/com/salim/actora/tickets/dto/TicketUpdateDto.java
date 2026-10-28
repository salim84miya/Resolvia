package com.salim.actora.tickets.dto;

import com.salim.actora.tickets.data.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketUpdateDto {

    @NotBlank(message = "Ticket id is required for updating status")
    private Long ticketId;

    @NotNull(message = "Ticket status is needed for updating status")
    private TicketStatus status;
}
