package com.salim.actora.tickets.services;

import com.salim.actora.tickets.data.TicketStatus;
import com.salim.actora.tickets.dto.ApiPageResponse;
import com.salim.actora.tickets.dto.CreateTicketDto;
import com.salim.actora.tickets.dto.SupportTicketResponseDto;
import com.salim.actora.tickets.dto.TicketUpdateDto;
import com.salim.actora.tickets.entity.SupportTicket;
import com.salim.actora.tickets.repository.SupportTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupportTicketServiceImpl implements SupportTicketService{


    private final SupportTicketRepository supportTicketRepository;

    @Override
    @Tool(description = "Returns existing support ticket "+
            "for the provided order id and customer id"+
            "customerId and orderId is of Long type" +
            "use for finding existing ticket for the provided order id +" +
            "and checking duplicate before creating new ticket")
    public List<SupportTicketResponseDto> getAllTicketForOrder(Long customerId, Long orderId) {

        Optional<List<SupportTicket>> supportTickets = supportTicketRepository
                .findAllByCustomerIdAndOrderIdOrderByCreatedAtDesc(customerId,orderId);

        return supportTickets.map(supportTicketsList->
                supportTicketsList.stream().map(supportTicket->
                        SupportTicketResponseDto
                        .builder()
                                .title(supportTicket.getTitle())
                                .ticketStatus(supportTicket.getStatus())
                                .description(supportTicket.getDescription())
                                .createdAt(supportTicket.getCreatedAt())
                                .id(supportTicket.getId())
                                .priority(supportTicket.getPriority())
                        .build())
                        .collect(Collectors.toList())).orElseGet(List::of);
    }


    @Override
    @Tool(description = "Create ticket with the provided information." +
            "order id in long" +
            "customer id in long" +
            "set ticket priority which can be HIGH,MEDIUM OR LOW"+
            "set ticket status which can be  OPEN,\n" +
            "    IN_PROGRESS,\n" +
            "    WAITING_FOR_CUSTOMER,\n" +
            "    RESOLVED,\n" +
            "    CLOSED"+
            "ticket title and description in string")
    public SupportTicketResponseDto createTicket(CreateTicketDto dto) {

        SupportTicket supportTicket = SupportTicket.builder()
                .orderId(dto.getOrderId())
                .customerId(dto.getCustomerId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(TicketStatus.OPEN)
                .priority(dto.getPriority())
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
                                        .priority(supportTicket.getPriority())
                                        .build()).collect(Collectors.toList())
                        ).orElseGet(List::of);

    }


    @Override
    public ApiPageResponse<SupportTicketResponseDto> getAllTickets(Pageable pageable,String title) {

        //getting data with page information
        Page<SupportTicket> supportTickets =null;

        if(title==null){
            supportTickets =supportTicketRepository.findAll(pageable);
        }else{
            supportTickets = supportTicketRepository.findAllByTitleContaining(pageable,title);

        }

            //Getting the data in our custom dto
            List<SupportTicketResponseDto> content = supportTickets.getContent().stream().map(supportTicket->
                    SupportTicketResponseDto.builder()
                            .title(supportTicket.getTitle())
                            .ticketStatus(supportTicket.getStatus())
                            .description(supportTicket.getDescription())
                            .createdAt(supportTicket.getCreatedAt())
                            .id(supportTicket.getId())
                            .priority(supportTicket.getPriority())
                            .build()).collect(Collectors.toList());

            //returning the data with page information
            return ApiPageResponse.<SupportTicketResponseDto>builder()
                    .content(content)
                    .pageNo(supportTickets.getNumber())
                    .pageSize(supportTickets.getSize())
                    .pageElement(supportTickets.getNumberOfElements())
                    .totalPages(supportTickets.getTotalPages())
                    .build();

    }

    @Override
    public SupportTicketResponseDto updateTicketStatus(TicketUpdateDto ticketUpdateDto) {

        SupportTicket supportTicket = supportTicketRepository.findById(ticketUpdateDto.getTicketId())
                .orElseThrow(()->new IllegalArgumentException("No ticket found with the id"));

        supportTicket.setStatus(ticketUpdateDto.getStatus());

        supportTicket =  supportTicketRepository.save(supportTicket);

        return SupportTicketResponseDto.builder()
                .title(supportTicket.getTitle())
                .ticketStatus(supportTicket.getStatus())
                .description(supportTicket.getDescription())
                .createdAt(supportTicket.getCreatedAt())
                .id(supportTicket.getId())
                .priority(supportTicket.getPriority())
                .build();
    }
}
