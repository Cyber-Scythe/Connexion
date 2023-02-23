package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.connexionservice.activity.requests.DeleteMessagesActivityRequest;
import com.nashss.se.connexionservice.activity.results.DeleteMessagesActivityResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DeleteMessagesLambda
        extends LambdaActivityRunner<DeleteMessagesActivityRequest, DeleteMessagesActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteMessagesActivityRequest>, LambdaResponse>

    {

        private final Logger log = LogManager.getLogger();

        @Override
        public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteMessagesActivityRequest> input,
                                            Context context) {
                log.info("Inside DeleteMessagesLambda");
                System.out.println("Inside delete messages lambda");

                        return super.runActivity(
                                () -> input.fromQuery(query ->
                                        DeleteMessagesActivityRequest.builder()
                                                .withSenderEmail(query.get("senderEmail"))
                                                .withDateTimeSent(query.get("dateTimeSent"))
                                                .build()),
                                (request, serviceComponent) ->
                                        serviceComponent.provideDeleteMessagesActivity().handleRequest(request)
                        );
        }
}

