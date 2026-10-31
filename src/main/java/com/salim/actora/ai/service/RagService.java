package com.salim.actora.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RagService {


    private final VectorStore vectorStore;

    private final ChatClient chatClient;

    private List<Document> searchForPolicies(String customerMsg){
        try{

            return  vectorStore.similaritySearch(
                    SearchRequest.builder()
                            .query(customerMsg)
                            .topK(10)
                            .similarityThreshold(0.5)
                            .build());
        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    @Tool(description = "This tool is used to retrieve user query related policies" +
            " for better responses" +
            " it takes user message as input in string format"+
    "It performs similarity search from vector store to find relevant documents"+
            "then pass it to llm to get response in string")
    public String rag(String customerMsg){


            List<Document> documents = searchForPolicies(customerMsg);

            String context = documents.stream()
                    .map(doc->"""
                Policy:
                %s

                Content:
                %s
                """.formatted(
                                    doc.getMetadata(),
                                    doc.getText()))
                    .collect(Collectors.joining("\n\n"));

            return "\n\n Retrieved Company Polices \n "+context;


    }
}
