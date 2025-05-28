package com.nashss.se.connexionservice.lambda;

import com.nashss.se.connexionservice.activity.requests.CreateNewUserActivityRequest;
import com.nashss.se.connexionservice.activity.results.CreateNewUserActivityResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateNewUserLambda
        extends LambdaActivityRunner<CreateNewUserActivityRequest, CreateNewUserActivityResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateNewUserActivityRequest>,
        LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateNewUserActivityRequest> input,
                                        Context context) {
        return super.runActivity(() -> {
                    CreateNewUserActivityRequest unauthenticatedRequest =
                            input.fromBody(CreateNewUserActivityRequest.class);

                    return input.fromUserClaims(claims -> CreateNewUserActivityRequest.builder()
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
                }, (request, serviceComponent) -> serviceComponent.provideCreateNewUserActivity()
                        .handleRequest(request)
        );
    }
}

