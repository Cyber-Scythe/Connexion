package com.nashss.se.connexionservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static com.nashss.se.connexionservice.utils.CollectionUtils.copyToList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;
    @Mock
    private PaginatedScanList<User> scanResult;

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

    @Test
    public void getAllConnexions_callsScan_ReturnsAllUsers() {
        // GIVEN
        User currUser = new User("id", "name", "email", 1, "city", "state", "TYPE",
                                List.of("hobby1", "hobby2", "hobby3", "hobby4"), null);

        when(dynamoDBMapper.scan(eq(User.class), any())).thenReturn(scanResult);

        ArgumentCaptor<DynamoDBScanExpression> scanExpressionArgumentCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        // WHEN
        List<User> scanList = userDao.getAllConnexions(currUser);

        // THEN
        verify(dynamoDBMapper).scan(eq(User.class), scanExpressionArgumentCaptor.capture());
        assertEquals(scanResult, scanList, "Expected method to return the results of the scan");
    }

    @Test
    public void getConnexions_scanWithFilterExpression_returnsListOfConnexions() {
        // GIVEN
        List<String> personalityTypes = List.of("T1", "T2", "T3", "T4", "T5", "T6");

        when(dynamoDBMapper.scan(eq(User.class), any())).thenReturn(scanResult);

        ArgumentCaptor<DynamoDBScanExpression> scanExpressionArgumentCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        // WHEN
        List<User> scanList = userDao.getConnexions(personalityTypes);

        // THEN
        verify(dynamoDBMapper).scan(eq(User.class), scanExpressionArgumentCaptor.capture());
        DynamoDBScanExpression scanExpression = scanExpressionArgumentCaptor.getValue();
        Map<String, AttributeValue> valueMap = scanExpression.getExpressionAttributeValues();

        assertNotNull(valueMap, "Expected the expression attribute value map to be set in the scan expression");
        assertEquals(valueMap.size(), 6, "Expected personality types to be set in " +
                "attribute value map");

        assertNotNull(scanExpression.getFilterExpression(), "Expected the scan expression to contain a filter" +
                " expression");

        assertEquals(scanResult, scanList, "Expected method to return the results of the scan");
    }

    @Test
    public void sortConnexions_sortsListOfConnexions_returnsMapOfSortedUsers() {
        // GIVEN
        Map<String, Integer> connexionTreeMap = new TreeMap<>();

        List<String> currUserHobbies = List.of("hobby1", "hobby2", "hobby3", "hobby4");

        User u1 = new User("8gffhrgdujff-7fhr", "u1", "u1@mail.com",
                            32, "city", "state", "TYPE2",
                            List.of("hobby2", "hobby3"), null);
        User u2 = new User("353-h7d-4hyfje", "u2", "u2@mail.com",
                        32, "city", "state", "TYPE1",
                             List.of("hobby1"), null);
        List<User> userList = List.of(u1, u2);

        connexionTreeMap.put(u1.getId(), 2);
        connexionTreeMap.put(u2.getId(), 1);

        // WHEN
        Map<String, Integer> sortedMap = userDao.sortConnexions(currUserHobbies, userList);

        // THEN
        assertEquals(connexionTreeMap, sortedMap);
    }
}

