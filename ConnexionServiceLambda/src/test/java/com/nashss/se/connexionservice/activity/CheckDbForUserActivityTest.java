package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.CheckDbForUserActivityRequest;
import com.nashss.se.connexionservice.activity.results.CheckDbForUserActivityResult;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CheckDbForUserActivityTest {
    @Mock
    private UserDao userDao;

    private CheckDbForUserActivity checkDbForUserActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        checkDbForUserActivity = new CheckDbForUserActivity(userDao);
    }

    @Test
    public void handleRequest_userFound_returnsUserModelInResult() {
        // GIVEN
        User expectedUser = new User();
        expectedUser.setId("expectedId");
        expectedUser.setName("expectedName");
        expectedUser.setEmail("expectedEmail");
        expectedUser.setAge(32);
        expectedUser.setCity("expectedCity");
        expectedUser.setState("expectedState");
        expectedUser.setPersonalityType("expectedPersonalityType");
        expectedUser.setHobbies(List.of("hobbies"));
        expectedUser.setConnexions(List.of("C1", "C2", "C3"));

        when(userDao.getUser(expectedUser.getId())).thenReturn(expectedUser);

        CheckDbForUserActivityRequest request = CheckDbForUserActivityRequest.builder()
                .withId(expectedUser.getId())
                .withName(expectedUser.getName())
                .withEmail(expectedUser.getEmail())
                .build();

        // WHEN
        CheckDbForUserActivityResult result = checkDbForUserActivity.handleRequest(request);

        // THEN
        assertEquals(expectedUser.getId(), result.getUser().getId());
        assertEquals(expectedUser.getName(), result.getUser().getName());
        assertEquals(expectedUser.getEmail(), result.getUser().getEmail());
        assertEquals(expectedUser.getAge(), result.getUser().getAge());
        assertEquals(expectedUser.getCity(), result.getUser().getCity());
        assertEquals(expectedUser.getState(), result.getUser().getState());
        assertEquals(expectedUser.getPersonalityType(), result.getUser().getPersonalityType());
        assertEquals(expectedUser.getHobbies(), result.getUser().getHobbies());
        assertEquals(expectedUser.getConnexions(), result.getUser().getConnexions());
    }

    @Test
    public void handleRequest_userNotFound_createsAndSavesNewUser() {
        // GIVEN
        String expectedId = "expectedId";
        String expectedName = "expectedName";
        String expectedEmail = "expectedEmail";

        User expectedUser = new User();
        expectedUser.setId(expectedId);
        expectedUser.setName(expectedName);
        expectedUser.setEmail(expectedEmail);

        when(userDao.getUser(expectedId)).thenReturn(null);

        CheckDbForUserActivityRequest request = CheckDbForUserActivityRequest.builder()
                .withId(expectedId)
                .withName(expectedName)
                .withEmail(expectedEmail)
                .build();

        // WHEN
        CheckDbForUserActivityResult result = checkDbForUserActivity.handleRequest(request);

        // THEN
        assertEquals(expectedId, result.getUser().getId());
        assertEquals(expectedName, result.getUser().getName());
        assertEquals(expectedEmail, result.getUser().getEmail());
    }
}