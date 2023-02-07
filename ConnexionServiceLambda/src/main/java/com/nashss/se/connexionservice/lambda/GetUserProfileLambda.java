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
            log.info("handleRequest");

            return super.runActivity(
                    () -> input.fromPath(path ->
                            GetUserProfileActivityRequest.builder()
                                    .withId(path.get("id"))
                                    .build()),
                    (request, serviceComponent) ->
                            serviceComponent.provideGetUserProfileActivity().handleRequest(request)
            );
        }
}
