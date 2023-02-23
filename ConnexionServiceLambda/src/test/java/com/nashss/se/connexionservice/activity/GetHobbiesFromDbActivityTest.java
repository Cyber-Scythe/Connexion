package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetHobbiesFromDbActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetHobbiesFromDbActivityResult;
import com.nashss.se.connexionservice.dynamodb.HobbyDao;
import com.nashss.se.connexionservice.dynamodb.models.Hobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetHobbiesFromDbActivityTest {
    @Mock
    private HobbyDao hobbyDao;

    private GetHobbiesFromDbActivity getHobbiesFromDbActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getHobbiesFromDbActivity = new GetHobbiesFromDbActivity(hobbyDao);
    }

    @Test
    public void handleRequest_getsHobbiesFromDb_returnsListOfHobbies() {
        // GIVEN
        Hobby H1 = new Hobby("hobby1");
        Hobby H2 = new Hobby("hobby2");
        Hobby H3 = new Hobby("hobby3");

        List<Hobby> listOfHobby = List.of(H1, H2, H3);

        when(hobbyDao.getHobbies()).thenReturn(listOfHobby);

        List<String> hobbyList = new ArrayList<>();

        for (Hobby hobby : listOfHobby) {
            hobbyList.add(hobby.getHobby());
        }

        GetHobbiesFromDbActivityRequest request = GetHobbiesFromDbActivityRequest.builder().build();

        // WHEN
        GetHobbiesFromDbActivityResult result = getHobbiesFromDbActivity.handleRequest(request);

        // THEN
         assertEquals(hobbyList, result.getHobbies());
    }
}
