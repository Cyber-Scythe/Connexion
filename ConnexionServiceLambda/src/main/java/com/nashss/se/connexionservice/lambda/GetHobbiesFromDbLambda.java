package com.nashss.se.connexionservice.lambda;

import com.nashss.se.connexionservice.activity.requests.GetHobbiesFromDbActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetHobbiesFromDbActivityResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetHobbiesFromDbLambda
        extends LambdaActivityRunner<GetHobbiesFromDbActivityRequest, GetHobbiesFromDbActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetHobbiesFromDbActivityRequest>,
    LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetHobbiesFromDbActivityRequest> input,
                                            Context context) {
        return super.runActivity(() -> {
                // GetUserProfileActivityRequest unauthenticatedRequest =
                // input.fromBody(GetUserProfileActivityRequest.class);
                return input.fromUserClaims(claims ->
                        GetHobbiesFromDbActivityRequest.builder()
                                .build());
            }, (request, serviceComponent) ->
                    serviceComponent.provideGetHobbiesFromDbActivity().handleRequest(request)
            );
    }
}


