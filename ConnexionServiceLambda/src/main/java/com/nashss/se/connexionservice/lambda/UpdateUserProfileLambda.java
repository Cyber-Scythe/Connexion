package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.nashss.se.connexionservice.activity.requests.UpdateUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.UpdateUserProfileActivityResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateUserProfileLambda
        extends LambdaActivityRunner<UpdateUserProfileActivityRequest, UpdateUserProfileActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateUserProfileActivityRequest>,
        LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateUserProfileActivityRequest> input,
                                        Context context) {
        return super.runActivity(
                () -> {
                    UpdateUserProfileActivityRequest unauthenticatedRequest =
                            input.fromBody(UpdateUserProfileActivityRequest.class);

                    return input.fromUserClaims(claims ->
                            UpdateUserProfileActivityRequest.builder()
                                    .withId(claims.get("sub"))
                                    .withName(unauthenticatedRequest.getName())
                                    .withEmail(claims.get("email"))
                                    .withAge(unauthenticatedRequest.getAge())
                                    .withCity(unauthenticatedRequest.getCity())
                                    .withState(unauthenticatedRequest.getState())
                                    .withPersonalityType(unauthenticatedRequest.getPersonalityType())
                                    .withHobbies(unauthenticatedRequest.getHobbies())
                                    .withConnexions(unauthenticatedRequest.getConnexions())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateUserProfileActivity().handleRequest(request)
        );
    }
}

