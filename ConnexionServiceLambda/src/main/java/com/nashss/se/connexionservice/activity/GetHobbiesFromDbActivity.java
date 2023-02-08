package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.dynamodb.HobbyDao;
import com.nashss.se.connexionservice.dynamodb.models.Hobby;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the GetPlaylistActivity for the MusicPlaylistService's GetPlaylist API.
 *
 * This API allows the customer to get one of their saved playlists.
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
     * If the hobby does not exist, this should throw a HobbyNotFoundException.
     *
     * @return GetHobbiesFromDbActivityResult result object
     */
    public GetHobbiesFromDbActivityResult handleRequest() {
        log.info("Inside GetCategoriesResult handleRequest");

        List<Hobby> hobbies = hobbyDao.getHobbies();
        List<String> hobbiesList = new ArrayList<>();

        for (Hobby hobby : hobbies) {
            hobbiesList.add(hobby.getHobby());
        }
        return GetHobbiesFromDbResult.builder()
                .withHobbies(hobbiesList)
                .build();
    }
}

