package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetConnexionsActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetConnexionsActivityResult;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

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
     * It then returns the list of connexions (User).
     * <p>
     * If the connexion does not exist, this should throw a ConnexionNotFound.
     *
     * @return GetConnexionsActivityResult result object
     */
    public GetConnexionsActivityResult handleRequest(final GetConnexionsActivityRequest getConnexionsActivityRequest) {
        log.info("Inside GetConnexionsActivityResult handleRequest");
        List<String> compatiblePersonalityTypes =
                userDao.getCompatiblePersonalityTypes(getConnexionsActivityRequest.getPersonalityType());

        List<String> connexions = userDao.getConnexions(compatiblePersonalityTypes);

        return GetConnexionsActivityResult.builder()
                .withConnexions(connexions)
                .build();
    }
}


