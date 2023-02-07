package com.nashss.se.connexionservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;

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
}

