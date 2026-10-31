package com.salim.actora.tickets.mapper;

import com.salim.actora.tickets.dto.SupportTicketResponseDto;
import com.salim.actora.tickets.entity.SupportTicket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TicketMapper {

    TicketMapper mapper = Mappers.getMapper(TicketMapper.class);

    @Mapping(source = "status",target = "ticketStatus")
    SupportTicketResponseDto mapSupportTicketToDto(SupportTicket ticket);
}
