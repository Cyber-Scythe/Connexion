package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetHobbiesFromDbActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetHobbiesFromDbActivityResult;
import com.nashss.se.connexionservice.dynamodb.HobbyDao;
import com.nashss.se.connexionservice.dynamodb.models.Hobby;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementation of the GetHobbiesFromDbActivity for Connexion's GetHobbiesFromDb API.
 *
 * This API gets a list of the user's hobbies from the database.
 */
public class GetHobbiesFromDbActivity {
    private final Logger log = LogManager.getLogger();
    private final HobbyDao hobbyDao;

    /**
     * Instantiates a new GetHobbyFromDbActivity object.
     *
     * @param hobbyDao HobbyDao to access the hobbies table.
     */
    @Inject
    public GetHobbiesFromDbActivity(HobbyDao hobbyDao) {

        this.hobbyDao = hobbyDao;
    }

    /**
     * This method handles the incoming request by retrieving the hobbies from the database.
     * <p>
     * It then returns the list of hobbies.
     * <p>
     * @param getHobbiesFromDbActivityRequest
     * @return GetHobbiesFromDbActivityResult result object
     */
    public GetHobbiesFromDbActivityResult handleRequest(final GetHobbiesFromDbActivityRequest
                                                                getHobbiesFromDbActivityRequest) {
        log.info("Inside GetCategoriesResult handleRequest");

        List<Hobby> hobbies = hobbyDao.getHobbies();
        List<String> hobbiesList = new ArrayList<>();

        for (Hobby hobby : hobbies) {
            hobbiesList.add(hobby.getHobby());
        }
        return GetHobbiesFromDbActivityResult.builder()
                .withHobbies(hobbiesList)
                .build();
    }
}

