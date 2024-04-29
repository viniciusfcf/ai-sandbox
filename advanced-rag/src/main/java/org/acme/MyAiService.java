package org.acme;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(retrievalAugmentor=RetrievalAugmentorExample.class)
public interface MyAiService {

    @UserMessage("""
        What my favourite color?
            """)
    String whoIsTheBest();

    String chat(@UserMessage String userMessage);
    
}