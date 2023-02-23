package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetConnexionProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetConnexionProfileActivityResult;
import com.nashss.se.connexionservice.converters.ModelConverter;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.models.UserModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetConnexionProfileActivity for the ConnexionService's GetConnexionProfile API.
 *
 * This API gets the profile of a user that is compatible with current user.
 */
public class GetConnexionProfileActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new GetConnexionProfileActivity object.
     *
     * @param userDao UserDao to access the connexions table.
     */
    @Inject
    public GetConnexionProfileActivity(UserDao userDao) {

        this.userDao = userDao;
    }

    /**
     * This method handles the incoming request by retrieving the compatible user from the database.
     * <p>
     * It then returns the compatible user.
     * <p>
     * @param getConnexionProfileActivityRequest The request made.
     * @return GetConnexionsActivityResult result object
     */
    public GetConnexionProfileActivityResult handleRequest(final GetConnexionProfileActivityRequest
                                                                   getConnexionProfileActivityRequest) {
        log.info("Inside GetConnexionsActivityResult handleRequest");

        User compatibleUser = userDao.getUser(getConnexionProfileActivityRequest.getId());
        UserModel userModel = new ModelConverter().toUserModel(compatibleUser);

        return GetConnexionProfileActivityResult.builder()
                .withUser(userModel)
                .build();
    }
}
