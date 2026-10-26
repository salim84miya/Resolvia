package com.salim.actora.tickets.services;

import com.salim.actora.tickets.data.TicketStatus;
import com.salim.actora.tickets.dto.CreateTicketDto;
import com.salim.actora.tickets.dto.SupportTicketResponseDto;
import com.salim.actora.tickets.entity.SupportTicket;
import com.salim.actora.tickets.repository.SupportTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupportTicketServiceImpl implements SupportTicketService{


    private final SupportTicketRepository supportTicketRepository;

    @Override
    @Tool(description = "Create ticket with the provided information." +
            "order id in long" +
            "customer id in long" +
            "ticket title and description in string")
    public SupportTicketResponseDto createTicket(CreateTicketDto dto) {

        SupportTicket supportTicket = SupportTicket.builder()
                .orderId(dto.getOrderId())
                .customerId(dto.getCustomerId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();

        supportTicket = supportTicketRepository.save(supportTicket);

        return SupportTicketResponseDto.builder()
                .id(supportTicket.getId())
                .ticketStatus(supportTicket.getStatus())
                .title(supportTicket.getTitle())
                .description(supportTicket.getDescription())
                .createdAt(supportTicket.getCreatedAt())
                .build();
    }

    @Override
    @Tool(description = "Returns the current ticket status for the provided ticket id in long.")
    public TicketStatus getCurrentTicketStatus(Long ticketId) {

        SupportTicket supportTicket = supportTicketRepository.findById(ticketId)
                .orElseThrow(()->new IllegalArgumentException("No ticket found with id"));

        return supportTicket.getStatus();
    }

    @Override
    @Tool(description = "Returns all the tickets of the customer with the provided customer id in long.")
    public List<SupportTicketResponseDto> getAllTicketByCustomer(Long customerId) {

        Optional<List<SupportTicket>> supportTickets = supportTicketRepository.findAllByCustomerIdOrderByCreatedAtDesc(customerId);

        return supportTickets.map(supportTicketsMapped->

                        supportTicketsMapped.stream().map(supportTicket->
                                SupportTicketResponseDto.builder()
                                        .title(supportTicket.getTitle())
                                        .ticketStatus(supportTicket.getStatus())
                                        .description(supportTicket.getDescription())
                                        .createdAt(supportTicket.getCreatedAt())
                                        .id(supportTicket.getId())
                                        .build()).collect(Collectors.toList())
                        ).orElseGet(List::of);

    }
}
