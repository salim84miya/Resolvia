package com.salim.actora.tickets.services;

import com.salim.actora.tickets.dto.CreateTicketDto;
import com.salim.actora.tickets.entity.SupportTicket;


public interface SupportTicketService {

    public SupportTicket createTicket(CreateTicketDto dto);



}
