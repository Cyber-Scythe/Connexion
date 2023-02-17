package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.connexionservice.activity.requests.GetUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetUserProfileActivityResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserProfileLambda
          extends LambdaActivityRunner<GetUserProfileActivityRequest, GetUserProfileActivityResult>
          implements RequestHandler<AuthenticatedLambdaRequest<GetUserProfileActivityRequest>, LambdaResponse> {

        private final Logger log = LogManager.getLogger();

        @Override
        public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetUserProfileActivityRequest> input, Context context) {
            return super.runActivity(
                    () -> {
                        // GetUserProfileActivityRequest unauthenticatedRequest = input.fromBody(GetUserProfileActivityRequest.class);
                        return input.fromUserClaims(claims ->
                                GetUserProfileActivityRequest.builder()
                                        .withEmail(claims.get("email"))
                                        .withName(claims.get("name"))
                                        .withId(claims.get("sub"))
                                        .build());
                    },
                    (request, serviceComponent) ->
                            serviceComponent.provideGetUserProfileActivity().handleRequest(request)
            );
        }
}
