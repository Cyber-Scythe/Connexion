package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.CheckDbForUserActivityRequest;
import com.nashss.se.connexionservice.activity.results.CheckDbForUserActivityResult;
import com.nashss.se.connexionservice.converters.ModelConverter;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the CheckDbForUserActivity for the Connexion's CheckDbForUser API.
 * <p>
 * This API checks the database to see if the user already exists. If they do not then
 * the API call to create a new user is triggered.
 */
public class CheckDbForUserActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new CheckDbForUser object.
     *
     * @param userDao UserDao to access the users table.
     */
    @Inject
    public CheckDbForUserActivity(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * This method handles the incoming request by persisting a new user
     * with the provided user's name, email, city, state, personalityType, and hobbies from the request.
     * <p>
     * It then returns the newly created user.
     * <p>
     *
     * @param checkDbForUserActivityRequest request object containing the user's name, email, city, state, personality type,
     *                                      and hobbies associated with it
     * @return CheckDbForUserActivityResult result object containing the API defined {@link UserModel}
     */
    public CheckDbForUserActivityResult handleRequest(final CheckDbForUserActivityRequest checkDbForUserActivityRequest) {

        log.info("Received CreateUserActivityRequest {}", checkDbForUserActivityRequest);


        User searchUser = userDao.getUser(checkDbForUserActivityRequest.getEmail(),
                                          checkDbForUserActivityRequest.getName(),
                                          checkDbForUserActivityRequest.getId());

        UserModel userModel = new ModelConverter().toUserModel(searchUser);

        return CheckDbForUserActivityResult.builder()
               .withUser(userModel)
                .build();
    }
}
