package org.acme;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface MyRAGAiService {

    @UserMessage("""
        What is my favorite color?
            """)
    String favoriteColor();

    
}