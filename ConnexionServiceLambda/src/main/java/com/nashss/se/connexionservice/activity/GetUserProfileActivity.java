package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetUserProfileActivityResult;
import com.nashss.se.connexionservice.converters.ModelConverter;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the GetPlaylistActivity for the MusicPlaylistService's GetPlaylist API.
 *
 * This API allows the customer to get one of their saved playlists.
 */
public class GetUserProfileActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new GetUserProfileActivity object.
     *
     * @param userDao UserDao to access the users table.
     */
    @Inject
    public GetUserProfileActivity(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * This method handles the incoming request by retrieving the user from the database.
     * <p>
     * It then returns the user.
     * <p>
     * If the user does not exist, this should throw a UserNotFoundException.
     *
     * @param getUserProfileActivityRequest request object containing the user's email
     * @return getUserProfileActivityResult result object containing the API defined {@link UserModel}
     */
    public GetUserProfileActivityResult handleRequest(final GetUserProfileActivityRequest getUserProfileActivityRequest) {
        log.info("Received GetPlaylistRequest {}", getUserProfileActivityRequest);

        String requestedEmail = getUserProfileActivityRequest.getEmail();
        String requestedName = getUserProfileActivityRequest.getName();
        String requestedId = getUserProfileActivityRequest.getId();

        User user = userDao.getUser(requestedEmail, requestedName, requestedId);
        UserModel userModel = new ModelConverter().toUserModel(user);

        return GetUserProfileActivityResult.builder()
                .withUser(userModel)
                .build();
    }
}

