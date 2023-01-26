package com.nashss.se.connexionservice.lambda;

import com.nashss.se.connexionservice.activity.requests.CreateUserActivityRequest;
import com.nashss.se.connexionservice.activity.results.CreateUserActivityResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class CreateUserLambda
        extends LambdaActivityRunner<CreateUserActivityRequest, CreateUserActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateUserActivityRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateUserActivityRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateUserActivityRequest unauthenticatedRequest = input.fromBody(CreateUserActivityRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateUserActivityRequest.builder()
                                    .withName(unauthenticatedRequest.getName())
                                    .withEmail(unauthenticatedRequest.getEmail())
                                    .withBirthdate(claims.get("birthdate"))
                                    .withCity(claims.get("city"))
                                    .withState(claims.get("state"))
                                    .withPersonalityType(claims.get("personalityType"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateUserActivity().handleRequest(request)
        );
    }
}
