package com.nashss.se.connexionservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.exceptions.UserNotFoundException;
import com.nashss.se.connexionservice.metrics.MetricsConstants;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Accesses data for a user using {@link User} to represent the model in DynamoDB.
 */
@Singleton
public class UserDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a PlaylistDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the playlists table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public UserDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Returns the {@link User} corresponding to the specified email.
     *
     * @param email the User email
     * @return the stored User, or null if none was found.
     */
    public User getUser(String email) {
        User user = this.dynamoDbMapper.load(User.class, email);

        if (user == null) {
            metricsPublisher.addCount(MetricsConstants.GETPLAYLIST_PLAYLISTNOTFOUND_COUNT, 1);
            throw new UserNotFoundException("Could not find user with email " + email);
        }
        metricsPublisher.addCount(MetricsConstants.GETPLAYLIST_PLAYLISTNOTFOUND_COUNT, 0);
        return user;
    }

    /**
     * Saves (creates or updates) the given user.
     *
     * @param user The user to save
     * @return The User object that was saved
     */
    public User saveUser(User user) {
        this.dynamoDbMapper.save(user);
        return user;
    }
}

