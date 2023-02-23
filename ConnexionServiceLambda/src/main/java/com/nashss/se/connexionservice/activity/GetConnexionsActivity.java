package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetConnexionsActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetConnexionsActivityResult;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        if (getConnexionsActivityRequest.getPersonalityType() != null &&
                !getConnexionsActivityRequest.getPersonalityType().isBlank()) {

            List<String> compatiblePersonalityTypes =
                    userDao.getCompatiblePersonalityTypes(getConnexionsActivityRequest.getPersonalityType());

            List<User> connexionsList = userDao.getConnexions(compatiblePersonalityTypes);

            Map<String, Integer> sortedConnexions = new HashMap<>();
            sortedConnexions = userDao.sortConnexions(currUser.getHobbies(), connexionsList);

            List<String> sortedConnexionList = new ArrayList<>(sortedConnexions.keySet());
            sortedConnexionList.remove(getConnexionsActivityRequest.getId());

            return GetConnexionsActivityResult.builder()
                    .withConnexionsList(sortedConnexionList)
                    .build();

        } else {

            List<String> connexionIdList = new ArrayList<>();
            List<User> connexionsList = userDao.getAllConnexions(currUser);

            for (User u : connexionsList) {
                connexionIdList.add(u.getId());
            }

            return GetConnexionsActivityResult.builder()
                    .withConnexionsList(connexionIdList)
                    .build();
        }
    }
}


