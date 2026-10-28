package com.salim.actora.tickets.services;

import com.salim.actora.tickets.data.TicketStatus;
import com.salim.actora.tickets.dto.ApiPageResponse;
import com.salim.actora.tickets.dto.CreateTicketDto;
import com.salim.actora.tickets.dto.SupportTicketResponseDto;
import com.salim.actora.tickets.entity.SupportTicket;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface SupportTicketService {

    public SupportTicketResponseDto createTicket(CreateTicketDto dto);

    public TicketStatus getCurrentTicketStatus(Long ticketId);

    public List<SupportTicketResponseDto> getAllTicketByCustomer(Long customerId);

    public List<SupportTicketResponseDto> getAllTicketForOrder(Long customerId,Long orderId);

    public ApiPageResponse<SupportTicketResponseDto> getAllTickets(Pageable pageable);

}
