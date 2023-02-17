package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.connexionservice.activity.requests.GetConnexionsActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetConnexionsActivityResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetConnexionsLambda
        extends LambdaActivityRunner<GetConnexionsActivityRequest, GetConnexionsActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetConnexionsActivityRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetConnexionsActivityRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    //GetConnexionsActivityRequest unauthenticatedRequest = input.fromBody(GetConnexionsActivityRequest.class);
                    GetConnexionsActivityRequest pathData = input.fromPath(path ->
                                GetConnexionsActivityRequest.builder()
                                        .withPersonalityType(path.get("personalityType"))
                                        .build());

                   return input.fromUserClaims(claims ->
                            GetConnexionsActivityRequest.builder()
                                    .withId(claims.get("sub"))
                                    .withPersonalityType(pathData.getPersonalityType())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetConnexionsActivity().handleRequest(request)
        );
    }
}
