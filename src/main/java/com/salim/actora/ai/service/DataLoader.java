package com.salim.actora.ai.service;

import org.springframework.ai.document.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DataLoader {

    List<Document> extractDocument(MultipartFile file);
}
