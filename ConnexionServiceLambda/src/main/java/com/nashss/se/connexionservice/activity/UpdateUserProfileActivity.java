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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


public class UpdateUserProfileActivity {
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
    public UpdateUserProfileActivity(UserDao userDao, MetricsPublisher metricsPublisher) {
        this.userDao = userDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the user, updating, and persisting the user.
     * <p>
     * It then returns the updated user.
     * <p>
     * @param updateUserProfileActivityRequest request object containing the playlist ID, playlist name,
     *                                         and customer ID associated with it
     * @return updateUserProfileActivityResult result object containing the API defined {@link UserModel}
     */
    public UpdateUserProfileActivityResult handleRequest(final UpdateUserProfileActivityRequest
                                                                 updateUserProfileActivityRequest) {

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

        if (!updateUserProfileActivityRequest.getPersonalityType().isBlank()) {
            List<String> compatiblePersonalityTypes =
                    userDao.getCompatiblePersonalityTypes(updateUserProfileActivityRequest.getPersonalityType());

            List<User> connexions = userDao.getConnexions(compatiblePersonalityTypes);
            List<String> connexionIDs = new ArrayList<>();
            connexions.forEach(u -> connexionIDs.add(u.getId()));
            connexionIDs.remove(user.getId());

            user.setConnexions(connexionIDs);

        } else {
            List<User> connexions = userDao.getAllConnexions(user);
            System.out.println("connexions: " + connexions);

            if (!user.getHobbies().isEmpty() && user.getHobbies() != null) {
                Map<String, Integer> sortedMap = userDao.sortConnexions(user.getHobbies(), connexions);

                List<String> sortedConnexions = new ArrayList<>(sortedMap.keySet());

                sortedConnexions.remove(user.getId());

                user.setConnexions(sortedConnexions);
            } else {
                List<String> connexionIds = new ArrayList<>();
                for (User u : connexions) {
                    connexionIds.add(u.getId());
                }
                user.setConnexions(connexionIds);
            }
        }

        userDao.saveUser(user);

        return UpdateUserProfileActivityResult.builder()
                .withUser(new ModelConverter().toUserModel(user))
                .build();
    }

}
