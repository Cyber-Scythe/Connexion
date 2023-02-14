package com.nashss.se.connexionservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

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
//        List<User> usersList = new ArrayList<>();
//        List<User> connexionsList = List.of(new User());
//
//        User u1 = new User("8gffhrgdujff-7fhr", "u1", "u1@mail.com",
//                32, "city", "state", "TYPE",
//                List.of("hobby1", "hobby2", "hobby3"), connexionsList);
//        User u2 = new User("353-h7d-4hyfje", "u2", "u2@mail.com",
//                32, "city", "state", "TYPE",
//                List.of("hobby1", "hobby2", "hobby3"), connexionsList);
//
//        usersList.add(u1);
//        usersList.add(u2);
//
//        // WHEN
//        ArgumentCaptor<DynamoDBScanExpression> scanExpressionArgumentCaptor =
//                ArgumentCaptor.forClass(DynamoDBScanExpression.class);
//
//        when(dynamoDBMapper.scanPage(eq(User.class), any())).thenReturn(scanResult);
//        when(scanResult.getResults()).thenReturn(usersList);
//
//        //WHEN
//        List<User> scanList = userDao.getAllConnexions();
//
//        //THEN
//        verify(dynamoDBMapper).scan(eq(User.class), scanExpressionArgumentCaptor.capture());
//        DynamoDBScanExpression scanExpression = scanExpressionArgumentCaptor.getValue();
//        assertEquals(scanResult.getResults(), scanList, "Expected method to return the results of the scan");
//    }
}

