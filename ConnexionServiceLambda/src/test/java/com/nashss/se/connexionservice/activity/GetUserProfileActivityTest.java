package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetUserProfileActivityResult;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetUserProfileActivityTest {
    @Mock
    private UserDao userDao;

    private GetUserProfileActivity getUserProfileActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getUserProfileActivity = new GetUserProfileActivity(userDao);
    }

    @Test
    public void handleRequest_withRequestedId_returnsUserProfileData() {
        // GIVEN
        User user = new User();
        user.setId("10101010101");
        user.setEmail("requestedEmail");
        user.setFirstName("requestedFirstName");
        user.setLastName("requestedLastName");
        user.setBirthMonth(2);
        user.setBirthDay(15);
        user.setBirthYear(1990);
        user.setCity("requestedCity");
        user.setState("requestedState");
        user.setPersonalityType("TYPE");
        user.setHobbies(List.of("hobby1", "hobby2", "hobby3"));
        user.setConnexions(List.of());

        when(userDao.getUser(user.getId())).thenReturn(user);

        GetUserProfileActivityRequest request = GetUserProfileActivityRequest.builder()
                .withId(user.getId())
                .withEmail(user.getEmail())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .build();

        // WHEN
        GetUserProfileActivityResult result = getUserProfileActivity.handleRequest(request);

        // THEN
        assertEquals(user.getId(), result.getUser().getId());
        assertEquals(user.getEmail(), result.getUser().getEmail());
        assertEquals(user.getFirstName(), result.getUser().getFirstName());
        assertEquals(user.getLastName(), result.getUser().getLastName());
        assertEquals(user.getBirthMonth(), result.getUser().getBirthMonth());
        assertEquals(user.getBirthDay(), result.getUser().getBirthDay());
        assertEquals(user.getBirthYear(), result.getUser().getBirthYear());
        assertEquals(user.getCity(), result.getUser().getCity());
        assertEquals(user.getState(), result.getUser().getState());
        assertEquals(user.getCountry(), result.getUser().getCountry());
        assertEquals(user.getPersonalityType(), result.getUser().getUserPersonalityType());
        assertEquals(user.getHobbies(), result.getUser().getHobbies());
        assertEquals(user.getConnexions(), result.getUser().getConnexions());
    }

//    @Test
//    public void handleRequest_userNotFound_createsAndSavesNewUser() {
//        // GIVEN
//        String expectedId = "expectedId";
//        String expectedName = "expectedName";
//        String expectedEmail = "expectedEmail";
//
//        User expectedUser = new User();
//        expectedUser.setId(expectedId);
//        expectedUser.setName(expectedName);
//        expectedUser.setEmail(expectedEmail);
//
//        when(userDao.getUser(expectedId)).thenReturn(null);
//
//        GetUserProfileActivityRequest request = GetUserProfileActivityRequest.builder()
//                .withId(expectedId)
//                .withName(expectedName)
//                .withEmail(expectedEmail)
//                .build();
//
//        // WHEN
//        GetUserProfileActivityResult result = getUserProfileActivity.handleRequest(request);
//
//        // THEN
//        assertEquals(expectedId, result.getUser().getId());
//        assertEquals(expectedName, result.getUser().getName());
//        assertEquals(expectedEmail, result.getUser().getEmail());
//    }
}
