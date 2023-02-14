package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.UpdateUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.UpdateUserProfileActivityResult;
import com.nashss.se.connexionservice.converters.ModelConverter;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;
import com.nashss.se.connexionservice.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;
import java.util.List;

public class UpdateUserProfileActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a new UpdatePlaylistActivity object.
     *
     * @param userDao PlaylistDao to access the playlist table.
     * @param metricsPublisher MetricsPublisher to publish metrics.
     */
    @Inject
    public UpdateUserProfileActivity(UserDao userDao, MetricsPublisher metricsPublisher) {
        //super(UpdatePlaylistRequest.class);
        this.userDao = userDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the playlist, updating it,
     * and persisting the playlist.
     * <p>
     * It then returns the updated playlist.
     * <p>
     * If the playlist does not exist, this should throw a PlaylistNotFoundException.
     * <p>
     * If the provided playlist name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     * <p>
     * If the request tries to update the customer ID,
     * this should throw an InvalidAttributeChangeException
     *
     * @param updateUserProfileActivityRequest request object containing the playlist ID, playlist name, and customer ID
     *                              associated with it
     * @return updateUserProfileActivityResult result object containing the API defined {@link UserModel}
     */
    public UpdateUserProfileActivityResult handleRequest(final UpdateUserProfileActivityRequest updateUserProfileActivityRequest) {
        log.info("Received UpdateUserProfileActivityRequest {}", updateUserProfileActivityRequest);

        User user = userDao.getUser(updateUserProfileActivityRequest.getId());

        user.setId(updateUserProfileActivityRequest.getId());
        user.setName(updateUserProfileActivityRequest.getName());
        user.setEmail(updateUserProfileActivityRequest.getEmail());
        user.setPersonalityType(updateUserProfileActivityRequest.getPersonalityType());
        user.setAge(updateUserProfileActivityRequest.getAge());
        user.setCity(updateUserProfileActivityRequest.getCity());
        user.setState(updateUserProfileActivityRequest.getState());
        user.setHobbies(updateUserProfileActivityRequest.getHobbies());

        List<String> compatiblePersonalityTypes =
                userDao.getCompatiblePersonalityTypes(updateUserProfileActivityRequest.getPersonalityType());

        List<User> connexions = userDao.getConnexions(compatiblePersonalityTypes);
        user.setConnexions(connexions);

        userDao.saveUser(user);

        return UpdateUserProfileActivityResult.builder()
                .withUser(new ModelConverter().toUserModel(user))
                .build();
    }

}
