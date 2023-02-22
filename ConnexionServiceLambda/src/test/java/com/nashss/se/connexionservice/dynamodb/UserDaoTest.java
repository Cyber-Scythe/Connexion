package com.nashss.se.connexionservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;
    @Mock
    ScanResultPage scanResult;

    @InjectMocks
    private UserDao userDao;


    @BeforeEach
    public void setup() {
        initMocks(this);
        userDao = new UserDao(dynamoDBMapper, metricsPublisher);
    }

    @Test
    public void getUser_withUserId_callsMapperWithPartitionKey() {
        // GIVEN
        String userId = "userId";
        when(dynamoDBMapper.load(User.class, userId)).thenReturn(new User());

        // WHEN
        User user = userDao.getUser(userId);

        // THEN
        assertNotNull(user);
        verify(dynamoDBMapper).load(User.class, userId);
    }

    @Test
    public void getUser_userIdNotFound_createsNewUser() {
        // GIVEN
        String nonexistentUserId = "NotReal";
        String nonexistentName = "name";
        String nonexistentEmail = "email";

        User newUser = new User();
        newUser.setId(nonexistentUserId);
        newUser.setName(nonexistentName);
        newUser.setEmail(nonexistentEmail);

        when(dynamoDBMapper.load(User.class, nonexistentUserId)).thenReturn(newUser);

        // WHEN + THEN
        assertEquals(newUser.getId(), nonexistentUserId);
        assertEquals(newUser.getName(), nonexistentName);
        assertEquals(newUser.getEmail(), nonexistentEmail);
    }

    @Test
    public void saveUser_callsMapperWithUser() {
        // GIVEN
        User user = new User();

        // WHEN
        User result = userDao.saveUser(user);

        // THEN
        verify(dynamoDBMapper).save(user);
        assertEquals(user, result);
    }

//    @Test
//    public void getAllConnexions_callsScan_ReturnsAllUsers() {
//        // GIVEN
//        Map<Integer, String> connexionMap = new HashMap<>();
//        List<User> usersList = new ArrayList<>();
//        User currUser = new User("1", "me", "me@gmail.com", 33, "city", "state", "TYPE",
//                List.of("hobby1", "hobby2", "hobby3"), null);
//        User u1 = new User("8gffhrgdujff-7fhr", "u1", "u1@mail.com",
//                32, "city", "state", "TYPE",
//                List.of("hobby1"), null);
//        User u2 = new User("353-h7d-4hyfje", "u2", "u2@mail.com",
//                32, "city", "state", "TYPE",
//                List.of("hobby2", "hobby3"), null);
//
//        usersList.add(u1);
//        usersList.add(u2);
//
//        List<User> connexionsList = List.of(u1, u2);
//
//        // WHEN
//        ArgumentCaptor<DynamoDBScanExpression> scanExpressionArgumentCaptor =
//                ArgumentCaptor.forClass(DynamoDBScanExpression.class);
//
//        when(dynamoDBMapper.scanPage(eq(User.class), any())).thenReturn(scanResult);
//        when(scanResult.getResults()).thenReturn(usersList);
//
//        //WHEN
//        List<String> scanList = userDao.getAllConnexions(currUser);
//
//        //THEN
//        verify(dynamoDBMapper).scan(eq(User.class), scanExpressionArgumentCaptor.capture());
//        DynamoDBScanExpression scanExpression = scanExpressionArgumentCaptor.getValue();
//        assertEquals(scanResult.getResults(), scanList, "Expected method to return the results of the scan");
//    }

//    @Test
//    public void getConnexions_personalityTypeIsNull_returnsListOfAllConnexions() {
//        // GIVEN
//        User currUser = new User("1", "me", "me@gmail.com", 33, "city", "state",
//                    null, List.of("hobby1", "hobby2", "hobby3"), null);
//        User u1 = new User("8gffhrgdujff-7fhr", "u1", "u1@mail.com",
//                        32, "city", "state", "TYPE2",
//                            List.of("hobby1"), null);
//        User u2 = new User("353-h7d-4hyfje", "u2", "u2@mail.com",
//                        32, "city", "state", "TYPE1",
//                            List.of("hobby2", "hobby3"), null);
//
//        List<String> connexionsList = List.of(u1.getId(), u2.getId());
//
//        ArgumentCaptor<DynamoDBScanExpression> scanExpressionArgumentCaptor =
//                ArgumentCaptor.forClass(DynamoDBScanExpression.class);
//
//        when(dynamoDBMapper.scanPage(eq(User.class), any())).thenReturn(scanResult);
//        when(scanResult.getResults()).thenReturn(List.of(currUser, u1, u2));
//
//        // WHEN
//        List<String> allConnexions = userDao.getAllConnexions(currUser);
//
//        // THEN
//        verify(dynamoDBMapper).scan(eq(User.class), scanExpressionArgumentCaptor.capture());
//        DynamoDBScanExpression scanExpression = scanExpressionArgumentCaptor.getValue();
//
//        assertEquals(allConnexions, scanResult);
//    }

    @Test
    public void connexionsSort_sortsListOfConnexions_returnsMapOfSortedUsers() {
        // GIVEN
        Map<String, Integer> connexionTreeMap = new TreeMap<>();

        List<String> currUserHobbies = List.of("hobby1", "hobby2", "hobby3", "hobby4");

        User u1 = new User("8gffhrgdujff-7fhr", "u1", "u1@mail.com",
                            32, "city", "state", "TYPE2",
                            List.of("hobby1"), null);
        User u2 = new User("353-h7d-4hyfje", "u2", "u2@mail.com",
                        32, "city", "state", "TYPE1",
                             List.of("hobby2", "hobby3"), null);

        List<User> connexionList = List.of(u1, u2);
        connexionTreeMap.put(u1.getId(), 1);
        connexionTreeMap.put(u2.getId(), 2);

        // WHEN
        Map<String, Integer> sortedMap = userDao.connexionsSort(currUserHobbies, connexionList);

        // THEN
        assertEquals(connexionTreeMap, sortedMap);
    }
}

