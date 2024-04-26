package org.acme;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class GreetingResource {

    @Inject
    MyAiService myAiService;

    @Inject
    EmbeddingStore<TextSegment> embeddingStore;

    @Inject
    EmbeddingModel embeddingModel;

    @GET
    @Path("save")
    @Produces(MediaType.TEXT_PLAIN)
    public String save(@QueryParam(value = "text") String text) throws InterruptedException {
        TextSegment segment = TextSegment.from(text);
        Embedding embedding = embeddingModel.embed(segment).content();
        embeddingStore.add(embedding, segment);
        Thread.sleep(1000); // to be sure that embeddings were persisted
        return "ok";
    }

    @GET
    @Path("chat")
    @Produces(MediaType.TEXT_PLAIN)
    public String chat(@QueryParam(value = "text") String text) throws InterruptedException {
        return myAiService.chat(text);
    }

    @GET
    @Path("who")
    @Produces(MediaType.TEXT_PLAIN)
    public String who() throws InterruptedException {

        

        // Example of query to the embeddingStore
        // Embedding queryEmbedding = embeddingModel.embed("What is your favourite
        // sport?").content();
        // List<EmbeddingMatch<TextSegment>> relevant =
        // embeddingStore.findRelevant(queryEmbedding, 1);
        // EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);

        // return embeddingMatch.score()+": "+embeddingMatch.embedded().text();
        return myAiService.whoIsTheBest();
    }
}