package org.acme;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.RegisterAiService.NoRetrievalAugmentorSupplier;

@RegisterAiService(
    retrievalAugmentor = NoRetrievalAugmentorSupplier.class

)
public interface MyAiService {

    @SystemMessage("You are a professional poet")
    @UserMessage("""
                Write a poem about {topic}. The poem should be {lines} lines long. Then send this poem by email.
            """)
    String writeAPoem(String topic, int lines);

    @UserMessage("""
        Who is the best person in my house?
            """)
    String whoIsTheBest();

    
}