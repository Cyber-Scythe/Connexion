package com.nashss.se.connexionservice.lambda;

import com.nashss.se.connexionservice.activity.requests.GetConnexionProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetConnexionProfileActivityResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;

public class GetConnexionProfileLambda
        extends LambdaActivityRunner<GetConnexionProfileActivityRequest, GetConnexionProfileActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetConnexionProfileActivityRequest>,
        LambdaResponse> {
    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetConnexionProfileActivityRequest> input,
                                            Context context) {

        return super.runActivity(() -> {
            GetConnexionProfileActivityRequest pathData = input.fromPath(path ->
                    GetConnexionProfileActivityRequest.builder()
                                .withId(path.get("userId"))
                                .build());

            return GetConnexionProfileActivityRequest.builder()
                        .withId(pathData.getId())
                        .build();
            }, (request, serviceComponent) ->
                    serviceComponent.provideGetConnexionProfileActivity().handleRequest(request)
        );
    }
}


