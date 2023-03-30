package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.connexionservice.activity.requests.GetPresignedUrlActivityRequest;

import com.nashss.se.connexionservice.activity.requests.GetUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetPresignedUrlActivityResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import java.sql.SQLException;

public class GetPresignedUrlLambda
        extends LambdaActivityRunner<GetPresignedUrlActivityRequest, GetPresignedUrlActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetPresignedUrlActivityRequest>,
                LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetPresignedUrlActivityRequest> input,
                                        Context context) {

        return super.runActivity(
                () -> {
                    // GetUserProfileActivityRequest unauthenticatedRequest =
                    // input.fromBody(GetUserProfileActivityRequest.class);

                    return input.fromUserClaims(claims -> GetPresignedUrlActivityRequest.builder()
                            .withId(claims.get("sub"))
                            .build());
                }, (request, serviceComponent) ->
                        serviceComponent.provideGetPresignedUrlActivity().handleRequest(request)
        );
    }
}
