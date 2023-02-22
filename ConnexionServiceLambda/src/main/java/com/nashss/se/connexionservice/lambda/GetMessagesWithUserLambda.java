package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.connexionservice.activity.requests.GetMessagesWithUserActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetMessagesWithUserActivityResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetMessagesWithUserLambda
        extends LambdaActivityRunner<GetMessagesWithUserActivityRequest, GetMessagesWithUserActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetMessagesWithUserActivityRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetMessagesWithUserActivityRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    //GetMessagesFromUserActivityRequest unauthenticatedRequest = input.fromBody(GetMessagesFromUserActivityRequest.class);

                    GetMessagesWithUserActivityRequest pathData = input.fromPath(path ->
                            GetMessagesWithUserActivityRequest.builder()
                                    .withOtherUserEmail(path.get("otherUserEmail"))
                                    .build());

                    return input.fromUserClaims(claims ->
                            GetMessagesWithUserActivityRequest.builder()
                                    .withUserId(claims.get("sub"))
                                    .withCurrUserEmail(claims.get("email"))
                                    .withOtherUserEmail(pathData.getOtherUserEmail())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetMessagesWithUserActivity().handleRequest(request)
        );
    }
}
