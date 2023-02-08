package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetHobbiesFromDbLambda  extends LambdaActivityRunner<GetHobbiesFromDbRequest, GetHobbiesFromDbResult>
        implements RequestHandler<LambdaRequest<GetHobbiesFromDbRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetHobbiesFromDbRequest> input, Context context) {
        log.info("handleRequest");

        return super.runActivity(() -> input.fromPath(path ->
                GetHobbiesFromDbRequest.builder()
                        .build()), (request, serviceComponent) ->
                serviceComponent.provideGetHobbiesFromDbActivity().handleRequest()
        );
    }
}