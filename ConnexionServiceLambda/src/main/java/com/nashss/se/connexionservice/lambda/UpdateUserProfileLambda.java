package com.nashss.se.connexionservice.lambda;

import com.nashss.se.connexionservice.activity.requests.UpdateUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.UpdateUserProfileActivityResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

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
        return super.runActivity(() -> {
            UpdateUserProfileActivityRequest unauthenticatedRequest =
                            input.fromBody(UpdateUserProfileActivityRequest.class);

            return input.fromUserClaims(claims -> UpdateUserProfileActivityRequest.builder()
                    .withId(unauthenticatedRequest.getId())
                    .withEmail(unauthenticatedRequest.getEmail())
                    .withFirstName(unauthenticatedRequest.getFirstName())
                    .withLastName(unauthenticatedRequest.getLastName())
                    .withGender(unauthenticatedRequest.getGender())
                    .withBirthMonth(unauthenticatedRequest.getBirthMonth())
                    .withBirthDay(unauthenticatedRequest.getBirthDay())
                    .withBirthYear(unauthenticatedRequest.getBirthYear())
                    .withCity(unauthenticatedRequest.getCity())
                    .withState(unauthenticatedRequest.getState())
                    .withCountry(unauthenticatedRequest.getCountry())
                    .withPersonalityType(unauthenticatedRequest.getPersonalityType())
                    .withHobbies(unauthenticatedRequest.getHobbies())
                    .withAboutMe(unauthenticatedRequest.getAboutMe())
                    .withConnexions(unauthenticatedRequest.getConnexions())
                    .build());
            }, (request, serviceComponent) -> serviceComponent.provideUpdateUserProfileActivity()
                .handleRequest(request)
        );
    }
}

