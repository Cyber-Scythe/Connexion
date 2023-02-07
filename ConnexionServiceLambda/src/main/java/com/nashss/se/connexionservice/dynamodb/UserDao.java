package com.nashss.se.connexionservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.metrics.MetricsConstants;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Accesses data for a user using {@link User} to represent the model in DynamoDB.
 */
@Singleton
public class UserDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a UserDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the users table
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
     * @return the stored User, or if null create a new user and save it.
     */
    public User getUser(String email, String name, String id) {
        User user = this.dynamoDbMapper.load(User.class, id);

        if (user == null) {

            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setId(id);

            saveUser(newUser);

            return newUser;
        } else {
            metricsPublisher.addCount(MetricsConstants.GETUSER_USERNOTFOUND_COUNT, 0);
            return user;
        }
    }

    public User getUser(String id) {
        return this.dynamoDbMapper.load(User.class, id);
    }

    /**
     * Saves (creates or updates) the given user.
     *
     * @param user The user to save
     */
    public User saveUser(User user) {
        this.dynamoDbMapper.save(user);
        return user;
    }

    /**
     * Perform a search (via a "scan") of the Users table for users matching the given criteria.
     *
     * Both "email" and "name" attributes are searched.
     * The criteria are an array of Strings. Each element of the array is search individually.
     * ALL elements of the criteria array must appear in the playlistName or the tags (or both).
     * Searches are CASE SENSITIVE.
     *
     * @param criteria an array of String containing search criteria.
     * @return a List of User objects that match the search criteria.
     */
    public List<User> searchUsers(String[] criteria) {
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();

        if (criteria.length > 0) {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            String valueMapNamePrefix = ":c";

            StringBuilder nameFilterExpression = new StringBuilder();
            StringBuilder tagsFilterExpression = new StringBuilder();

            for (int i = 0; i < criteria.length; i++) {
                valueMap.put(valueMapNamePrefix + i,
                        new AttributeValue().withS(criteria[i]));
                nameFilterExpression.append(
                        filterExpressionPart("email", valueMapNamePrefix, i));
                tagsFilterExpression.append(
                        filterExpressionPart("name", valueMapNamePrefix, i));
            }

            dynamoDBScanExpression.setExpressionAttributeValues(valueMap);
            dynamoDBScanExpression.setFilterExpression(
                    "(" + nameFilterExpression + ") or (" + tagsFilterExpression + ")");
        }

        return this.dynamoDbMapper.scan(User.class, dynamoDBScanExpression);
    }

    private StringBuilder filterExpressionPart(String target, String valueMapNamePrefix, int position) {
        String possiblyAnd = position == 0 ? "" : "and ";
        return new StringBuilder()
                .append(possiblyAnd)
                .append("contains(")
                .append(target)
                .append(", ")
                .append(valueMapNamePrefix).append(position)
                .append(") ");
    }
}

