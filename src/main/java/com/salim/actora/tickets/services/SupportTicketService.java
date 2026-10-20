package com.salim.actora.tickets.services;

import com.salim.actora.tickets.data.TicketStatus;
import com.salim.actora.tickets.dto.CreateTicketDto;
import com.salim.actora.tickets.entity.SupportTicket;

import java.util.List;


public interface SupportTicketService {

    public SupportTicket createTicket(CreateTicketDto dto);

    public TicketStatus getCurrentTicketStatus(Long ticketId);

    public List<SupportTicket> getAllTicketByCustomer(Long customerId);


}
