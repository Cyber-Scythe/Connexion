package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.connexionservice.activity.requests.GetPresignedDownloadUrlActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetPresignedDownloadUrlActivityResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetPresignedDownloadUrlLambda
        extends LambdaActivityRunner<GetPresignedDownloadUrlActivityRequest, GetPresignedDownloadUrlActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetPresignedDownloadUrlActivityRequest>,
        LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetPresignedDownloadUrlActivityRequest> input,
                                        Context context) {

        return super.runActivity(
                () -> {
                    // GetUserProfileActivityRequest unauthenticatedRequest =
                    // input.fromBody(GetUserProfileActivityRequest.class);

                    return input.fromUserClaims(claims -> GetPresignedDownloadUrlActivityRequest.builder()
                            .withId(claims.get("sub"))
                            .build());
                }, (request, serviceComponent) ->
                        serviceComponent.provideGetPresignedDownloadUrlActivity().handleRequest(request)
        );
    }
}
