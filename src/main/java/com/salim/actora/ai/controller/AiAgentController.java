package com.salim.actora.ai.controller;

import com.salim.actora.orders.services.OrderService;
import com.salim.actora.tickets.services.SupportTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AiAgentController {

    private final ChatClient chatClient;
    private final OrderService orderService;
    private final SupportTicketService supportTicketService;


    @GetMapping("/ai")
    public String askAi(@RequestParam String prompt){
        return chatClient
                .prompt(prompt)
                .advisors(advisorSpec ->
                        advisorSpec.param(ChatMemory.CONVERSATION_ID,1L))
                .tools(orderService,supportTicketService)
                .call().content();
    }

}
