package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.connexionservice.activity.requests.GetUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetUserProfileActivityResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserProfileLambda
          extends LambdaActivityRunner<GetUserProfileActivityRequest, GetUserProfileActivityResult>
          implements RequestHandler<LambdaRequest<GetUserProfileActivityRequest>, LambdaResponse> {

        private final Logger log = LogManager.getLogger();

        @Override
        public LambdaResponse handleRequest(LambdaRequest<GetUserProfileActivityRequest> input, Context context) {
            log.info("handleRequest");

            return super.runActivity(
                    () -> input.fromPath(path ->
                            GetUserProfileActivityRequest.builder()
                                    .withEmail(path.get("email"))
                                    .build()),
                    (request, serviceComponent) ->
                            serviceComponent.provideGetUserProfileActivity().handleRequest(request)
            );
    }
}
