package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.CreateNewUserActivityRequest;
import com.nashss.se.connexionservice.activity.requests.UpdateUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.CreateNewUserActivityResult;
import com.nashss.se.connexionservice.activity.results.UpdateUserProfileActivityResult;
import com.nashss.se.connexionservice.converters.ModelConverter;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;
import com.nashss.se.connexionservice.models.UserModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class CreateNewUserActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a new UpdateUserProfileActivity object.
     *
     * @param userDao UserDao to access the users table.
     * @param metricsPublisher MetricsPublisher to publish metrics.
     */
    @Inject
    public CreateNewUserActivity(UserDao userDao, MetricsPublisher metricsPublisher) {
        this.userDao = userDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the user, updating, and persisting the user.
     * <p>
     * It then returns the updated user.
     * <p>
     * @param createNewUserActivityRequest request object containing the playlist ID, playlist name,
     *                                         and customer ID associated with it
     * @return createNewUserActivityResult result object containing the API defined {@link UserModel}
     */
    public CreateNewUserActivityResult handleRequest(final CreateNewUserActivityRequest
                                                                 createNewUserActivityRequest) {

        log.info("Received CreateNewUserActivityRequest {}", createNewUserActivityRequest);


            User newUser = new User();

            newUser.setId(createNewUserActivityRequest.getId());
            newUser.setEmail(createNewUserActivityRequest.getEmail());
            newUser.setFirstName(createNewUserActivityRequest.getFirstName());
            newUser.setLastName(createNewUserActivityRequest.getLastName());
            newUser.setGender(createNewUserActivityRequest.getGender());
            newUser.setPersonalityType(createNewUserActivityRequest.getPersonalityType());
            newUser.setBirthMonth(createNewUserActivityRequest.getBirthMonth());
            newUser.setBirthDay(createNewUserActivityRequest.getBirthDay());
            newUser.setBirthYear(createNewUserActivityRequest.getBirthYear());
            newUser.setCity(createNewUserActivityRequest.getCity());
            newUser.setState(createNewUserActivityRequest.getState());
            newUser.setCountry(createNewUserActivityRequest.getCountry());
            newUser.setHobbies(createNewUserActivityRequest.getHobbies());
            newUser.setAboutMe(createNewUserActivityRequest.getAboutMe());
            newUser.setConnexions(createNewUserActivityRequest.getConnexions());

            userDao.saveUser(newUser);

            return CreateNewUserActivityResult.builder()
                    .withUser(new ModelConverter().toUserModel(newUser))
                    .build();
    }
}
