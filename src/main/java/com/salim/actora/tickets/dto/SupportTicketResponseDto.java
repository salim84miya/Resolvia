package com.salim.actora.tickets.dto;

import com.salim.actora.tickets.data.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupportTicketResponseDto {

    private Long id;

    private TicketStatus ticketStatus;

    private String title;

    private String description;

    private LocalDateTime createdAt;
}
