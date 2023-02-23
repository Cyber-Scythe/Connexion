package com.nashss.se.connexionservice.lambda;

import com.nashss.se.connexionservice.activity.requests.GetUserInboxActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetUserInboxActivityResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserInboxLambda
        extends LambdaActivityRunner<GetUserInboxActivityRequest, GetUserInboxActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetUserInboxActivityRequest>,
        LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetUserInboxActivityRequest> input,
                                        Context context) {
        return super.runActivity(() -> {
            //GetUserInboxActivityRequest unauthenticatedRequest =
            // input.fromBody(GetUserInboxActivityRequest.class);

            return input.fromUserClaims(claims ->
                    GetUserInboxActivityRequest.builder()
                            .withUserId(claims.get("sub"))
                            .withCurrUserEmail(claims.get("email"))
                            .build());
            }, (request, serviceComponent) ->
                serviceComponent.provideGetUserInboxActivity().handleRequest(request)
        );
    }
}
