package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetConnexionsActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetConnexionsActivityResult;
import com.nashss.se.connexionservice.converters.ModelConverter;
import com.nashss.se.connexionservice.dynamodb.UserDao;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.models.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetConnexionsActivityTest {
    @Mock
    private UserDao userDao;

    private GetConnexionsActivity getConnexionsActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getConnexionsActivity = new GetConnexionsActivity(userDao);
    }

    @Test
    public void handleRequest_withPersonalityType_returnsListOfSortedConnexions() {
        // GIVEN
        User user = new User();
        user.setId("10101010010");
        user.setHobbies(List.of("hobby1", "hobby3", "hobby4"));

        User u1 = new User();
        u1.setId("111100010101");
        u1.setPersonalityType("ENFP");
        u1.setHobbies(List.of("hobby1", "hobby2", "hobby4"));

        User u2 = new User();
        u2.setId("124343243242");
        u2.setPersonalityType("ENTJ");
        u2.setHobbies(List.of("hobby4"));

        User u3 = new User();
        u3.setId("14543324324");
        u3.setPersonalityType("INTJ");
        u3.setHobbies(List.of());

        List<User> userList = List.of(user, u1, u2, u3);
        List<User> allUsers = List.of(u1, u2, u3);
        List<UserModel> userModelAllUsers = new ModelConverter().toUserModelList(allUsers);

        List<String> compatiblePersonalityTypeList = List.of("ENFP", "INFP", "ENTJ", "ESFP", "ISFP", "INTJ");

        Map<User, Integer> sortedConnexions = new HashMap<>();
        sortedConnexions.put(u1, 2);
        sortedConnexions.put(u2, 1);
        sortedConnexions.put(u3, 0);

        List<String> sortedConnexionIdList = new ArrayList<>();
        for (User u : sortedConnexions.keySet()) {
            sortedConnexionIdList.add(u.getId());
        }

        when(userDao.getUser(user.getId())).thenReturn(user);
        when(userDao.getCompatiblePersonalityTypes(user.getPersonalityType())).
                thenReturn(compatiblePersonalityTypeList);
        when(userDao.getConnexions(user, compatiblePersonalityTypeList)).thenReturn(allUsers);
        when(userDao.sortConnexions(user.getHobbies(), allUsers)).thenReturn(sortedConnexions);
        when(userDao.getAllConnexions(user)).thenReturn(allUsers);

        GetConnexionsActivityRequest request = GetConnexionsActivityRequest.builder()
                .withId(user.getId())
                .withPersonalityType(user.getPersonalityType())
                .build();

        // WHEN
        GetConnexionsActivityResult result = getConnexionsActivity.handleRequest(request);

        // THEN
        assertEquals(userModelAllUsers, result.getConnexions());
    }
}
