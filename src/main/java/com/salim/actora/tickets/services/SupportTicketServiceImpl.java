package com.salim.actora.tickets.services;

import com.salim.actora.tickets.dto.CreateTicketDto;
import com.salim.actora.tickets.entity.SupportTicket;
import com.salim.actora.tickets.repository.SupportTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupportTicketServiceImpl implements SupportTicketService{


    private final SupportTicketRepository supportTicketRepository;

    @Override
    @Tool(description = "Create ticket with the provided information." +
            "order id in long" +
            "customer id in long" +
            "ticket title and description in string")
    public SupportTicket createTicket(CreateTicketDto dto) {

        SupportTicket supportTicket = new SupportTicket();

        supportTicket.setOrderId(dto.getOrderId());
        supportTicket.setCustomerId(dto.getCustomerId());
        supportTicket.setTitle(dto.getTitle());
        supportTicket.setDescription(dto.getDescription());

        return supportTicketRepository.save(supportTicket);
    }
}
