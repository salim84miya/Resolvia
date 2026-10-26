package com.salim.actora.ai.controller;

import com.salim.actora.ai.service.AiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AiAgentController {

    private final AiService aiService;


    @GetMapping("/ai")
    public ResponseEntity<?> askAi(@RequestParam String prompt, HttpServletRequest request){

        var result = aiService.aiResponse(prompt,request);

        return ResponseEntity.ok(result);
    }

}
