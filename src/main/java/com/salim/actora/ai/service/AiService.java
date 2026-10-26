package com.salim.actora.ai.service;

import com.salim.actora.auth.config.JwtService;
import com.salim.actora.orders.services.OrderService;
import com.salim.actora.tickets.services.SupportTicketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;
    private final OrderService orderService;
    private final SupportTicketService supportTicketService;
    private final JwtService jwtService;

    public String aiResponse(String prompt, HttpServletRequest request){

          UUID userId = getUserIdFromToken(request);

         return chatClient
                .prompt(prompt)
                .advisors(advisorSpec ->
                        advisorSpec.param(ChatMemory.CONVERSATION_ID,userId))
                .tools(orderService,supportTicketService)
                .call().content();
    }

    public UUID getUserIdFromToken(HttpServletRequest request){

       String accessTokenString =  request.getHeader("Authorization");

       String accessToken = accessTokenString.split("Bearer ")[1];

       return UUID.fromString( (String) jwtService.parse(accessToken).get("id"));


    }
}
