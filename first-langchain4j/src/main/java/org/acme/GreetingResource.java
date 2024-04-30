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

@Path("/")
public class GreetingResource {

    @Inject
    MyAiService myAiService;

    @Inject
    TriageService triage;

    @Inject
    RequestTriageService requestTriageService;

    

    @GET
    @Path("who")
    @Produces(MediaType.TEXT_PLAIN)
    public String who() {
        return myAiService.whoIsTheBest();
    }


    @GET
    @Path("poem")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return myAiService.writeAPoem("Flower", 4);
    }

    @GET
    @Path("review")
    @Produces(MediaType.APPLICATION_JSON)
    public TriagedReview review() {
        return triage.triage("I really love this bank. Not!");
    }

    @GET
    @Path("request-1")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestTriageResponse request1() {
        return requestTriageService.triage("Yesterday after a lot of rain a big hole appeared in front of my house, it needs to be repaired as soon as possible otherwise several vehicles will be damaged");
    }

    @GET
    @Path("request-12")
    @Produces(MediaType.APPLICATION_JSON)
    public Object request12() {
        return requestTriageService.triageToString("Yesterday after a lot of rain a big hole appeared in front of my house, it needs to be repaired as soon as possible otherwise several vehicles will be damaged");
    }

    @GET
    @Path("request-2")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestTriageResponse request2() {
        return requestTriageService.triage("I just rented an apartment at XPTO and I have no energy power, I would like to request activation");
    }

    @GET
    @Path("request-3")
    @Produces(MediaType.APPLICATION_JSON)
    public RequestTriageResponse request3() {
        return requestTriageService.triage("I'm sick and trying to sleep, in the front bar there's a band making a lot of noise. Could you call the police and check?");
    }
}


@RegisterAiService
interface TriageService {
    @SystemMessage("""
            You are working as a request proxy. You are an AI processing requests about different areas. You need to triage the requests into positive and negative ones.
            You will always answer with a JSON document, and only this JSON document.
            """)
    @UserMessage("""
        Your task is to process the review delimited by ---.
        Apply sentiment analysis to the review to determine
        if it is positive or negative, considering various languages.

        For example:
        - 'I love your bank, you are the best!' is a 'POSITIVE' review
        - 'J'adore votre banque' is a 'POSITIVE' review
        - 'I hate your bank, you are the worst!' is a 'NEGATIVE' review

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

@RegisterAiService
interface RequestTriageService {
    
    @SystemMessage("""
            You are working as a request proxy. You are an AI processing requests about different areas. You need to triage the requests into positive and negative ones.
            You will always answer with a JSON document, and only this JSON document.
            """)
    @UserMessage("""
        Your task is to process the review delimited by ---.
        Apply a sentiment analysis to the passed review to determine if it is positive or negative.
        The review can be in any language. So, you will need to identify the language.

        Apply classification to the review to determine the destination area to the request.
        if it is about car or street classify as DETRAN
        if it is about energy, power, tree, light classify as CEAL
        if you do not know the destination, classify as DONTKNOW

        Answer with a JSON document containing:
        - the 'evaluation' key set to 'POSITIVE' if the request is positive, 'NEGATIVE' otherwise
        - the 'classification' key set to 'DETRAN', 'CEAL' or 'DONTKNOW'
        - the 'sumarization' key set the sumarization of the original request with a maximum of 10 words

        ---
        {review}
        ---
        """)
    RequestTriageResponse triage(String review);

    @SystemMessage("""
            You are working as a request proxy. You are an AI processing requests about different areas. You need to triage the requests into positive and negative ones.
            You will always answer with a JSON document, and only this JSON document.
            """)
    @UserMessage("""
        Your task is to process the request delimited by ---.
        Apply sentiment analysis to the request to determine
        if it is positive or negative, considering various languages.
        

        For example:
        - 'I love your work, you are the best!' is a 'POSITIVE' request
        - 'J'adore votre banque' is a 'POSITIVE' request
        - 'I hate your work, you are the worst!' is a 'NEGATIVE' request

        Apply classification to the request to determine the destination area to the request.
        if it is about car or street classify as DETRAN
        if it is about energy, tree, light classify as CEAL
        if you do not know the destination, classify as DONTKNOW

        For example:
        - 'After the rain the street it is not ok' is a 'DETRAN' request
        - 'My house it is without energy' is a 'CEAL' request
        

        Respond with a JSON document containing:
        - the 'evaluation' key set to 'POSITIVE' if the request is
        positive, 'NEGATIVE' otherwise
        - the 'classification' key set to 'DETRAN', 'CEAL' or 'DONTKNOW'
        - the 'sumarization' key set the sumarization of the original request with a maximum of 10 words

        ---
        {request}
        ---
    """)
    String triageToString(String request);
}

record TriagedReview(Evaluation evaluation, String message) {
    @JsonCreator
    public TriagedReview {}
}

record RequestTriageResponse(Evaluation evaluation, String classification, String sumarization) {
    @JsonCreator
    public RequestTriageResponse {}
}

enum Evaluation {
    POSITIVE,
    NEGATIVE
}