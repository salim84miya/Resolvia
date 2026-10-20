package com.salim.actora.ai.controller;

import com.salim.actora.orders.services.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AiAgentController {

    private final ChatClient chatClient;
    private final OrderServiceImpl orderService;


    @GetMapping("ai")
    public String askAi(@RequestParam String prompt){
        return chatClient
                .prompt(prompt)
                .tools(orderService)
                .call().content();
    }

}
