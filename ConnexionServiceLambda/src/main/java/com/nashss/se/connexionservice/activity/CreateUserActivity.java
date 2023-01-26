package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.CreateUserActivityRequest;
import com.nashss.se.connexionservice.activity.results.CreateUserActivityResult;
import com.nashss.se.connexionservice.converters.ModelConverter;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.models.UserModel;
import com.nashss.se.connexionservice.utils.ConnexionServiceUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.management.InvalidAttributeValueException;

/**
 * Implementation of the CreatePlaylistActivity for the MusicPlaylistService's CreatePlaylist API.
 * <p>
 * This API allows the customer to create a new playlist with no songs.
 */
public class CreateUserActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new CreateUserActivity object.
     *
     * @param userDao UserDao to access the users table.
     */
    @Inject
    public CreateUserActivity(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * This method handles the incoming request by persisting a new user
     * with the provided user's name, email, city, state, personalityType, and hobbies from the request.
     * <p>
     * It then returns the newly created user.
     * <p>
     * If the provided name, email, city, state, personalityType, or hobbies has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createUserActivityRequest request object containing the user's name, email, city, state, personality type,
     *                                  and hobbies associated with it
     * @return createUserActivityResult result object containing the API defined {@link UserModel}
     */
    public CreateUserActivityResult handleRequest(final CreateUserActivityRequest createUserActivityRequest)
            throws InvalidAttributeValueException {

        log.info("Received CreateUserActivityRequest {}", createUserActivityRequest);

        if (!ConnexionServiceUtils.isValidString(createUserActivityRequest.getName())) {
            throw new InvalidAttributeValueException("User's name [" + createUserActivityRequest.getName() +
                    "] contains illegal characters");
        }

        Set<String> hobbies = null;
        if (createUserActivityRequest.getHobbies() != null) {
            hobbies = new HashSet<>(createUserActivityRequest.getHobbies());
        }

        Set<String> connections = null;
        if (createUserActivityRequest.getConnections() != null) {
            connections = new HashSet<>(createUserActivityRequest.getConnections());
        }

        User newUser = new User();
        newUser.setName(createUserActivityRequest.getName());
        newUser.setEmail(createUserActivityRequest.getEmail());
        newUser.setCity(createUserActivityRequest.getCity());
        newUser.setState(createUserActivityRequest.getState());
        newUser.setPersonalityType(createUserActivityRequest.getPersonalityType());
        newUser.setHobbies(hobbies);
        newUser.setConnections(connections);

        userDao.saveUser(newUser);

        UserModel userModel = new ModelConverter().toUserModel(newUser);
        return CreateUserActivityResult.builder()
                .withUser(userModel)
                .build();
    }
}
