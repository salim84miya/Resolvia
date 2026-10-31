package com.salim.actora.ai.service;

import com.salim.actora.auth.config.JwtService;
import com.salim.actora.orders.services.OrderService;
import com.salim.actora.tickets.services.SupportTicketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;
    private final OrderService orderService;
    private final SupportTicketService supportTicketService;
    private final RagService ragService;
    private final JwtService jwtService;
    private final VectorStore vectorStore;
    private final PdfReader pdfReader;
    private final TokenSplitter tokenSplitter;

    @Value("classpath:/prompt/ai-assistant-prompt.st")
    private Resource systemPrompt;

    public String aiResponse(String userMsg, HttpServletRequest request){

          UUID userId = getUserIdFromToken(request);

          String customerInfo = "Customer id "+userId;

//          String companyPolicies = ragService.rag(userMsg);

         return chatClient
                .prompt()
                 .user(userMsg+customerInfo)
                 .system(systemPrompt)
                .advisors(advisorSpec ->
                        advisorSpec.param(ChatMemory.CONVERSATION_ID,userId))
                .tools(orderService,supportTicketService,ragService)
                .call().content();
    }

    public UUID getUserIdFromToken(HttpServletRequest request){

       String accessTokenString =  request.getHeader("Authorization");

       String accessToken = accessTokenString.split("Bearer ")[1];

       return UUID.fromString( (String) jwtService.parse(accessToken).get("id"));


    }


    @Transactional
    public void addDocument(MultipartFile file){

        try{
           List<Document> documents = pdfReader.extractDocument(file);

           List<Document> splitDocuments =  tokenSplitter.splitCustomized(documents);

           vectorStore.add(splitDocuments);

           System.out.println("Document added successfully");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e){

            e.printStackTrace();
            throw  e;
        }


    }
}
