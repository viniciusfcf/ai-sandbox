package org.acme;


import com.fasterxml.jackson.annotation.JsonCreator;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    MyAiService myAiService;

    @Inject
    TriageService triage;

    @GET
    @Path("who")
    @Produces(MediaType.TEXT_PLAIN)
    public String who() {
        return myAiService.whoIsTheBest();
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST "+myAiService.writeAPoem("Flower", 2);
    }

    @GET
    @Path("2")
    @Produces(MediaType.APPLICATION_JSON)
    public TriagedReview hello2() {
        return triage.triage("I really love this bank. Not!");
    }
}


@RegisterAiService
interface TriageService {
    @SystemMessage("""
        You are working for a bank, processing reviews about
        financial products. Triage reviews into positive and
        negative ones, responding with a JSON document.
        """
    )
    @UserMessage("""
        Your task is to process the review delimited by ---.
        Apply sentiment analysis to the review to determine
        if it is positive or negative, considering various languages.

        For example:
        - `I love your bank, you are the best!` is a 'POSITIVE' review
        - `J'adore votre banque` is a 'POSITIVE' review
        - `I hate your bank, you are the worst!` is a 'NEGATIVE' review

        Respond with a JSON document containing:
        - the 'evaluation' key set to 'POSITIVE' if the review is
        positive, 'NEGATIVE' otherwise
        - the 'message' key set to a message thanking or apologizing
        to the customer. These messages must be polite and match the
        review's language.

        ---
        {review}
        ---
    """)
    TriagedReview triage(String review);
}

record TriagedReview(Evaluation evaluation, String message) {
    @JsonCreator
    public TriagedReview {}
}

enum Evaluation {
    POSITIVE,
    NEGATIVE
}