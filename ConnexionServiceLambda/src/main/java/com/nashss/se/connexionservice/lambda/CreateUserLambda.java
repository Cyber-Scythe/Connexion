package com.nashss.se.connexionservice.lambda;

import com.nashss.se.connexionservice.activity.requests.CreateUserActivityRequest;
import com.nashss.se.connexionservice.activity.results.CreateUserActivityResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import javax.management.InvalidAttributeValueException;


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
                                    .withEmail(unauthenticatedRequest.getEmail())
                                    .build());
                },
                (request, serviceComponent) ->
                {
                    try {
                        return serviceComponent.provideCreateUserActivity().handleRequest(request);
                    } catch (InvalidAttributeValueException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}
