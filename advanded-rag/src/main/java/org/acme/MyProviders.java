package org.acme;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.elasticsearch.ElasticsearchEmbeddingStore;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

public class MyProviders {

     @ConfigProperty(name = "quarkus.elasticsearch.hosts")
    String hosts;
    
    @Produces
    @Named("elasticsearch")
    public EmbeddingStore<TextSegment> embeddingStore() {
        return  ElasticsearchEmbeddingStore.builder()
                .serverUrl("http://" + hosts)
                .dimension(384)
                .build();
    }
}
