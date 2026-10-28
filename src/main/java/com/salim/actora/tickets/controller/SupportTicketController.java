package com.salim.actora.tickets.controller;

import com.salim.actora.tickets.dto.SupportTicketResponseDto;
import com.salim.actora.tickets.entity.SupportTicket;
import com.salim.actora.tickets.services.SupportTicketService;
import com.salim.actora.util.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class SupportTicketController {

    private final SupportTicketService ticketService;


    @GetMapping("/fetch")
    public ResponseEntity<?> getAllTickets(
            @RequestParam(required = false,defaultValue = "1") int pageNo,
            @RequestParam(required = false,defaultValue = "3") int pageSize,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "ASC") String sortDir,
            @RequestParam(required = false) String filter
            ){

        Sort sort = null;

        if(sortDir.equalsIgnoreCase("ASC")){
            sort = Sort.by(sortBy).ascending();
        }else{
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);

        var body = ticketService.getAllTickets(pageable,filter);

       return ResponseBuilder.build(body,null,HttpStatus.OK,LocalDateTime.now());
    }


}
