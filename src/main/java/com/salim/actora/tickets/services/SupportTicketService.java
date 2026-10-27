package com.salim.actora.tickets.services;

import com.salim.actora.tickets.data.TicketStatus;
import com.salim.actora.tickets.dto.CreateTicketDto;
import com.salim.actora.tickets.dto.SupportTicketResponseDto;

import java.util.List;
import java.util.Optional;


public interface SupportTicketService {

    public SupportTicketResponseDto createTicket(CreateTicketDto dto);

    public TicketStatus getCurrentTicketStatus(Long ticketId);

    public List<SupportTicketResponseDto> getAllTicketByCustomer(Long customerId);

    public List<SupportTicketResponseDto> getAllTicketForOrder(Long customerId,Long orderId);

}
