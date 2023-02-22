//package com.nashss.se.connexionservice.activity;
//
//import com.nashss.se.connexionservice.activity.requests.CheckDbForUserActivityRequest;
//import com.nashss.se.connexionservice.activity.results.CheckDbForUserActivityResult;
//import com.nashss.se.connexionservice.dynamodb.UserDao;
//import com.nashss.se.connexionservice.dynamodb.models.User;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.Mock;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.initMocks;
//
//public class CheckDbForUserActivityTest {
//    @Mock
//    private UserDao userDao;
//
//    private CheckDbForUserActivity checkDbForUserActivity;
//
//    @BeforeEach
//    public void setUp() {
//        initMocks(this);
//        checkDbForUserActivity = new CheckDbForUserActivity(userDao);
//    }
//
//    @Test
//    public void handleRequest_userFound_returnsUserModelInResult() {
//        // GIVEN
//        String expectedId = "expectedId";
//        String expectedName = "expectedName";
//        String expectedEmail = "expectedEmail";
//        int expectedAge = 32;
//        String expectedCity = "expectedCity";
//        String expectedState = "expectedState";
//        String expectedPersonalityType = "expectedType";
//        List<String> expectedHobbies = List.of("hobbies");
//
//        List<String> expectedConnexions = null;
//
//        User user = new User();
//        user.setId(expectedId);
//        user.setName(expectedName);
//        user.setEmail(expectedEmail);
//        user.setAge(expectedAge);
//        user.setCity(expectedCity);
//        user.setState(expectedState);
//        user.setPersonalityType(expectedPersonalityType);
//        user.setHobbies(expectedHobbies);
//        user.setConnexions(expectedConnexions);
//
//        when(userDao.getUser(expectedId)).thenReturn(user);
//
//        CheckDbForUserActivityRequest request = CheckDbForUserActivityRequest.builder()
//                .withId(expectedId)
//                .build();
//
//        // WHEN
//        CheckDbForUserActivityResult result = checkDbForUserActivity.handleRequest(request);
//
//        // THEN
//        assertEquals(expectedId, result.getUser().getId());
//        assertEquals(expectedName, result.getUser().getName());
//        assertEquals(expectedEmail, result.getUser().getEmail());
//        assertEquals(expectedAge, result.getUser().getAge());
//        assertEquals(expectedCity, result.getUser().getCity());
//        assertEquals(expectedState, result.getUser().getState());
//        assertEquals(expectedPersonalityType, result.getUser().getPersonalityType());
//        assertEquals(expectedHobbies, result.getUser().getHobbies());
//        assertEquals(expectedConnexions, result.getUser().getConnexions());
//    }
//}