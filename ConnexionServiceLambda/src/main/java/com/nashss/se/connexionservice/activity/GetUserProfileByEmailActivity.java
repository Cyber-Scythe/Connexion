package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.requests.GetUserProfileByEmailActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetUserProfileActivityResult;
import com.nashss.se.connexionservice.activity.results.GetUserProfileByEmailActivityResult;
import com.nashss.se.connexionservice.converters.ModelConverter;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetUserProfileByEmailActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new GetUserProfileByEmailActivity object.
     *
     * @param userDao UserDao to access the users table.
     */
    @Inject
    public GetUserProfileByEmailActivity(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * This method handles the incoming request by retrieving the user from the database.
     * <p>
     * It then returns the user.
     * <p>
     * If the user does not exist, this should throw a UserNotFoundException.
     *
     * @param getUserProfileByEmailActivityRequest request object containing the user's email
     * @return getUserProfileByEmailActivityResult result object containing the API defined {@link UserModel}
     */
    public GetUserProfileByEmailActivityResult handleRequest(final GetUserProfileByEmailActivityRequest getUserProfileByEmailActivityRequest) {
        log.info("Received GetUserProfileByEmailActivityRequest {}", getUserProfileByEmailActivityRequest);

        String userEmail = getUserProfileByEmailActivityRequest.getUserEmail();
        User user = userDao.getUserByEmail(userEmail);
        UserModel userModel = new ModelConverter().toUserModel(user);

        return GetUserProfileByEmailActivityResult.builder()
                .withUser(userModel)
                .build();
    }
}
