package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.connexionservice.activity.requests.GetUserProfileByEmailActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetUserProfileByEmailActivityResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserProfileByEmailLambda
        extends LambdaActivityRunner<GetUserProfileByEmailActivityRequest, GetUserProfileByEmailActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetUserProfileByEmailActivityRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetUserProfileByEmailActivityRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    // GetUserProfileActivityRequest unauthenticatedRequest = input.fromBody(GetUserProfileActivityRequest.class);

                    return input.fromPath(path ->
                            GetUserProfileByEmailActivityRequest.builder()
                                    .withUserEmail(path.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetUserProfileByEmailActivity().handleRequest(request)
        );
    }
}
