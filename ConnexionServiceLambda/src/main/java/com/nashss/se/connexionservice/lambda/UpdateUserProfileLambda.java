package com.nashss.se.connexionservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.connexionservice.activity.requests.GetUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.requests.UpdateUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.UpdateUserProfileActivityResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;

public class UpdateUserProfileLambda
        extends LambdaActivityRunner<UpdateUserProfileActivityRequest, UpdateUserProfileActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateUserProfileActivityRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateUserProfileActivityRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    //UpdateUserProfileActivityRequest unauthenticatedRequest = input.fromBody(UpdateUserProfileActivityRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateUserProfileActivityRequest.builder()
                                    .withId(claims.get("id"))
                                    .withEmail(claims.get("email"))
                                    .withBirthdate(claims.get("birthdate"))
                                    .withCity(claims.get("city"))
                                    .withState(claims.get("state"))
                                    .withPersonalityType(claims.get("personalityType"))
                                    .withHobbies(Collections.singletonList(claims.get("hobbies")))
                                    .withConnections(Collections.singletonList(claims.get("connections")))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateUserProfileActivity().handleRequest(request)
        );
    }
}
