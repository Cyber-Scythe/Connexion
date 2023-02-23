package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.connexionservice.activity.requests.CheckDbForUserActivityRequest;
import com.nashss.se.connexionservice.activity.results.CheckDbForUserActivityResult;


public class CheckDbForUserLambda extends LambdaActivityRunner<CheckDbForUserActivityRequest,
        CheckDbForUserActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CheckDbForUserActivityRequest>, LambdaResponse> {
        @Override
        public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CheckDbForUserActivityRequest> input,
                                        Context context) {
            return super.runActivity(
                    () -> {
                       // CheckDbForUserActivityRequest unauthenticatedRequest =
                        // input.fromBody(CheckDbForUserActivityRequest.class);
                        return input.fromUserClaims(claims ->
                                CheckDbForUserActivityRequest.builder()
                                        .withEmail(claims.get("email"))
                                        .withName(claims.get("name"))
                                        .withId(claims.get("sub"))
                                        .build());
                    },
                    (request, serviceComponent) ->
                            serviceComponent.provideCheckDbForUserActivity().handleRequest(request)
            );
        }
}

