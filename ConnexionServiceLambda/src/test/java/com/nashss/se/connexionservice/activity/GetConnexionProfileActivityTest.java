package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetConnexionProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetConnexionProfileActivityResult;
import com.nashss.se.connexionservice.converters.ModelConverter;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.models.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetConnexionProfileActivityTest {
    @Mock
    private UserDao userDao;
    UserModel userModel;

    private GetConnexionProfileActivity getConnexionProfileActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getConnexionProfileActivity = new GetConnexionProfileActivity(userDao);
    }

    @Test
    public void handleRequest_userFound_returnsUserModelInResult() {
        // GIVEN
        String expectedId = "expectedId";
        String expectedName = "expectedName";
        String expectedEmail = "expectedEmail";
        int expectedAge = 32;
        String expectedCity = "expectedCity";
        String expectedState = "expectedState";
        String expectedPersonalityType = "expectedPersonalityType";
        List<String> expectedHobbies = List.of("hobbies");

        List<String> expectedConnexions = null;

        User user = new User();
        user.setId(expectedId);
        user.setName(expectedName);
        user.setEmail(expectedEmail);
        user.setAge(expectedAge);
        user.setCity(expectedCity);
        user.setState(expectedState);
        user.setPersonalityType(expectedPersonalityType);
        user.setHobbies(expectedHobbies);
        user.setConnexions(expectedConnexions);

        userModel = new ModelConverter().toUserModel(user);

        when(userDao.getUser(expectedId)).thenReturn(user);

        GetConnexionProfileActivityRequest request = GetConnexionProfileActivityRequest.builder()
                .withId(expectedId)
                .build();

        // WHEN
        GetConnexionProfileActivityResult result = getConnexionProfileActivity.handleRequest(request);

        // THEN
        assertEquals(userModel, result.getUserModel());
    }
}
