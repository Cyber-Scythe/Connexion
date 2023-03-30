package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetConnexionsActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetConnexionsActivityResult;
import com.nashss.se.connexionservice.converters.ModelConverter;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;

import com.nashss.se.connexionservice.models.UserModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import javax.inject.Inject;


/**
 * Implementation of the GetConnexionsActivity for the ConnexionService's GetConnexions API.
 *
 * This API allows the user to get a list of users they are compatible with.
 */
public class GetConnexionsActivity {
    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;

    /**
     * Instantiates a new GetConnexionsActivity object.
     *
     * @param userDao UserDao to access the connexions table.
     */
    @Inject
    public GetConnexionsActivity(UserDao userDao) {

        this.userDao = userDao;
    }

    /**
     * This method handles the incoming request by retrieving the connexions (Users) from the database.
     * <p>
     * It then returns the list of connexions (Users).
     * <p>
     * @param getConnexionsActivityRequest The request made.
     * @return GetConnexionsActivityResult The result object to return.
     */
    public GetConnexionsActivityResult handleRequest(final GetConnexionsActivityRequest
                                                             getConnexionsActivityRequest) {
        log.info("Inside GetConnexionsActivityResult handleRequest");

        User currUser = userDao.getUser(getConnexionsActivityRequest.getId());
        System.out.println("CurrUser: " + currUser);

        if (getConnexionsActivityRequest.getPersonalityType() != null &&
                !getConnexionsActivityRequest.getPersonalityType().isBlank()) {

            List<String> compatiblePersonalityTypes =
                    userDao.getCompatiblePersonalityTypes(getConnexionsActivityRequest.getPersonalityType());

            List<User> connexionsList = new ArrayList<>(userDao.getConnexions(currUser, compatiblePersonalityTypes));
            List<UserModel> userModelConnexionsList = new ModelConverter().toUserModelList(connexionsList);

            Map<User, Integer> sortedConnexions = new HashMap<>();
            sortedConnexions = userDao.sortConnexions(currUser.getHobbies(), connexionsList);
            List<User> sortedConnexionList = new ArrayList<>(sortedConnexions.keySet());
            Collections.reverse(sortedConnexionList);

            return GetConnexionsActivityResult.builder()
                    .withConnexionsList(userModelConnexionsList)
                    .build();

        } else {

            List<User> connexionsList = userDao.getAllConnexions(currUser);
            List<UserModel> UserModelConnexionsList = new ModelConverter().toUserModelList(connexionsList);

            return GetConnexionsActivityResult.builder()
                    .withConnexionsList(UserModelConnexionsList)
                    .build();
        }
    }

    /**
     * Gets the profile information for each user.
     * @param connexionIdList The list of connexion user IDs
     * @return List of user profiles
     */
    public List<User> getConnexionProfileInfo(List<String> connexionIdList) {
        List<User> connexionProfileList = new ArrayList<>();

        for (String id : connexionIdList) {
            User user = userDao.getUser(id);
            connexionProfileList.add(user);
        }

        return connexionProfileList;
    }
}


