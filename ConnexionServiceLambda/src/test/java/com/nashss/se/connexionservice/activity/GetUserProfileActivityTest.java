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
        user.setName("requestedName");
        user.setAge(11);
        user.setCity("requestedCity");
        user.setState("requestedState");
        user.setPersonalityType("TYPE");
        user.setHobbies(List.of("hobby1", "hobby2", "hobby3"));
        user.setConnexions(List.of());

        when(userDao.getUser(user.getId())).thenReturn(user);

        GetUserProfileActivityRequest request = GetUserProfileActivityRequest.builder()
                .withId(user.getId())
                .withEmail(user.getEmail())
                .withName(user.getName())
                .build();

        // WHEN
        GetUserProfileActivityResult result = getUserProfileActivity.handleRequest(request);

        // THEN
        assertEquals(user.getId(), result.getUser().getId());
        assertEquals(user.getEmail(), result.getUser().getEmail());
        assertEquals(user.getName(), result.getUser().getName());
        assertEquals(user.getAge(), result.getUser().getAge());
        assertEquals(user.getCity(), result.getUser().getCity());
        assertEquals(user.getState(), result.getUser().getState());
        assertEquals(user.getPersonalityType(), result.getUser().getUserPersonalityType());
        assertEquals(user.getHobbies(), result.getUser().getHobbies());
        assertEquals(user.getConnexions(), result.getUser().getConnexions());
    }
}
