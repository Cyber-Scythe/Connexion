package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.UpdateUserProfileActivityRequest;
import com.nashss.se.connexionservice.activity.results.UpdateUserProfileActivityResult;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UpdateUserProfileActivityTest {
    @Mock
    private UserDao userDao;
    private MetricsPublisher metricsPublisher;
    private UpdateUserProfileActivity updateUserProfileActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        updateUserProfileActivity = new UpdateUserProfileActivity(userDao, metricsPublisher);
    }

//    @Test
//    public void handleRequest_withNewProfileDate_updatesUsersProfile() {
//        // GIVEN
//        List<String> hobbyList = List.of("h1", "h2", "h3");
//
//        User updatedUser = new User();
//        updatedUser.setId("10101010101");
//        updatedUser.setEmail("userEmail");
//        updatedUser.setName("name");
//        updatedUser.setAge(11);
//        updatedUser.setCity("city");
//        updatedUser.setState("state");
//        updatedUser.setPersonalityType("INTJ");
//        updatedUser.setHobbies(hobbyList);
//
//        when(userDao.getUser(updatedUser.getId())).thenReturn(updatedUser);
//
//        List<String> connexionList = new ArrayList<>();
//
//        if (!updatedUser.getPersonalityType().isBlank() || updatedUser.getPersonalityType() != null) {
//            List<String> compatiblePersonalityTypes =
//                    userDao.getCompatiblePersonalityTypes(updatedUser.getPersonalityType());
//
//            List<User> connexions = userDao.getConnexions(updatedUser, compatiblePersonalityTypes);
//            Map<User, Integer> sortedConnexionsMap = new TreeMap<>();
//            sortedConnexionsMap = userDao.sortConnexions(updatedUser.getHobbies(), connexions);
//
//            List<String> connexionIdList = new ArrayList<>();
//            for (User u : sortedConnexionsMap.keySet()) {
//                connexionIdList.add(u.getId());
//            }
//
//            updatedUser.setConnexions(connexionIdList);
//        } else {
//            List<User> connexionsList = userDao.getAllConnexions(updatedUser);
//
//            for (User u : connexionsList) {
//                connexionList.add(u.getId());
//            }
//            updatedUser.setConnexions(connexionList);
//        }
//
//        when(userDao.saveUser(updatedUser)).thenReturn(updatedUser);
//
//        UpdateUserProfileActivityRequest request = UpdateUserProfileActivityRequest.builder()
//                .withId(updatedUser.getId())
//                .withName(updatedUser.getName())
//                .withEmail(updatedUser.getEmail())
//                .withAge(updatedUser.getAge())
//                .withCity(updatedUser.getCity())
//                .withState(updatedUser.getState())
//                .withPersonalityType(updatedUser.getPersonalityType())
//                .withHobbies(updatedUser.getHobbies())
//                .withConnexions(updatedUser.getConnexions())
//                .build();
//
//        // WHEN
//        UpdateUserProfileActivityResult result = updateUserProfileActivity.handleRequest(request);
//
//        // THEN
//        assertEquals(updatedUser.getId(), result.getUser().getId());
//        assertEquals(updatedUser.getEmail(), result.getUser().getEmail());
//        assertEquals(updatedUser.getName(), result.getUser().getName());
//        assertEquals(updatedUser.getAge(), result.getUser().getAge());
//        assertEquals(updatedUser.getCity(), result.getUser().getCity());
//        assertEquals(updatedUser.getState(), result.getUser().getState());
//        assertEquals(updatedUser.getPersonalityType(), result.getUser().getUserPersonalityType());
//        assertEquals(updatedUser.getHobbies(), result.getUser().getHobbies());
//        assertEquals(updatedUser.getConnexions(), result.getUser().getConnexions());
//    }
}
