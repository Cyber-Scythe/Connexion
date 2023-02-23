package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.connexionservice.activity.requests.SendNewMessageActivityRequest;
import com.nashss.se.connexionservice.activity.results.SendNewMessageActivityResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class SendNewMessageLambda
        extends LambdaActivityRunner<SendNewMessageActivityRequest, SendNewMessageActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<SendNewMessageActivityRequest>,
        LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<SendNewMessageActivityRequest> input,
                                        Context context) {
        return super.runActivity(
                () -> {
                    SendNewMessageActivityRequest unauthenticatedRequest =
                            input.fromBody(SendNewMessageActivityRequest.class);

                    return input.fromUserClaims(claims ->
                            SendNewMessageActivityRequest.builder()
                                    .withSenderEmail(claims.get("email"))
                                    .withRecipientEmail(unauthenticatedRequest.getRecipientEmail())
                                    .withDateTimeSent(LocalDateTime.now().toString())
                                    .withMessageContent(unauthenticatedRequest.getMessageContent())
                                    .withReadStatus(unauthenticatedRequest.getReadStatus())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideSendNewMessageActivity().handleRequest(request)
        );
    }
}

