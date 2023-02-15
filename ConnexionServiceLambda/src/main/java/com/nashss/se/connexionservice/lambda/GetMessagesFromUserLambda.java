package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.connexionservice.activity.requests.GetMessagesFromUserActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetMessagesFromUserActivityResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetMessagesFromUserLambda
        extends LambdaActivityRunner<GetMessagesFromUserActivityRequest, GetMessagesFromUserActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetMessagesFromUserActivityRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetMessagesFromUserActivityRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    //GetMessagesFromUserActivityRequest unauthenticatedRequest = input.fromBody(GetMessagesFromUserActivityRequest.class);

                    GetMessagesFromUserActivityRequest pathData = input.fromPath(path ->
                            GetMessagesFromUserActivityRequest.builder()
                                    .withOtherUserEmail(path.get("otherUserEmail"))
                                    .build());

                    return input.fromUserClaims(claims ->
                            GetMessagesFromUserActivityRequest.builder()
                                    .withUserId(claims.get("sub"))
                                    .withCurrUserEmail(claims.get("email"))
                                    .withOtherUserEmail(pathData.getOtherUserEmail())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetMessagesFromUserActivity().handleRequest(request)
        );
    }
}
