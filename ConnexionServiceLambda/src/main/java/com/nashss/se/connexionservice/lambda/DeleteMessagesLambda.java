package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.connexionservice.activity.requests.DeleteMessagesActivityRequest;
import com.nashss.se.connexionservice.activity.results.DeleteMessagesActivityResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;

public class DeleteMessagesLambda
        extends LambdaActivityRunner<DeleteMessagesActivityRequest, DeleteMessagesActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteMessagesActivityRequest>, LambdaResponse>

    {

        private final Logger log = LogManager.getLogger();

        @Override
        public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteMessagesActivityRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    DeleteMessagesActivityRequest unauthenticatedRequest = input.fromBody(DeleteMessagesActivityRequest.class);

                    return input.fromUserClaims(claims ->
                            DeleteMessagesActivityRequest.builder()
                                    .withSenderEmail(claims.get("email"))
                                    .withRecipientEmail(unauthenticatedRequest.getRecipientEmail())
                                    .withDateTimeSent(unauthenticatedRequest.getDateTimeSent())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteMessagesActivity().handleRequest(request)
        );
    }
}
