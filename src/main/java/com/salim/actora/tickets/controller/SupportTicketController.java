package com.salim.actora.tickets.controller;

import com.salim.actora.tickets.dto.CreateTicketDto;
import com.salim.actora.tickets.dto.TicketUpdateDto;
import com.salim.actora.tickets.services.SupportTicketService;
import com.salim.actora.util.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @PostMapping("/create")
    public ResponseEntity<?> createTicket(@RequestBody @Valid CreateTicketDto dto){

        var body = ticketService.createTicket(dto);

        return ResponseBuilder.build(body,null,HttpStatus.OK,LocalDateTime.now());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTicketStatus(@RequestBody @Valid TicketUpdateDto dto){

        var body = ticketService.updateTicketStatus(dto);

        return ResponseBuilder.build(body,null,HttpStatus.OK,LocalDateTime.now());
    }

}
