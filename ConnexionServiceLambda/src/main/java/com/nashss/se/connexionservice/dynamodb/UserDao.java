package com.nashss.se.connexionservice.dynamodb;

import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
     * Returns the user corresponding to the id.
     * @param id The user's id
     * @return the stored user
     */
    public User getUser(String id) {
        return this.dynamoDbMapper.load(User.class, id);
    }


    /**
     * Saves (creates or updates) the given user.
     *
     * @param user The user to save
     * @return user Returns the user that was saved
     */
    public User saveUser(User user) {
        this.dynamoDbMapper.save(user);
        return user;
    }


    /**
     * Perform a search ("via a scan") of the users table.
     * @param currUser The current user
     * @return a List of all User objects in the table
     */
    public List<User> getAllConnexions(User currUser) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<User> scanResult = dynamoDbMapper.scan(User.class, scanExpression);

        scanResult.remove(currUser);
        return scanResult;
    }


    /**
     * Perform a search (via a "scan") of the users table for users matching the given criteria.
     * "personalityType" attribute is searched.
     * @param personalityTypes A list of compatible personality types.
     * @return a List of User objects that match the search criteria.
     */
    public List<User> getConnexions(List<String> personalityTypes) {
        Map<String, AttributeValue> valueMap = new HashMap<>();

        for (int i = 0; i < personalityTypes.size(); i++) {
            valueMap.put(":personalityType" + i, new AttributeValue().withS(personalityTypes.get(i)));
        }

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("personalityType = :personalityType0 OR personalityType = " +
                        ":personalityType1 OR personalityType = :personalityType2 OR personalityType = " +
                        ":personalityType3 OR personalityType = :personalityType4 OR personalityType = " +
                        ":personalityType5")
                .withExpressionAttributeValues(valueMap);

        return dynamoDbMapper.scan(User.class, scanExpression);
    }


    /**
     * Return a list of compatible personality types for the given personality type.
     * @param personalityType The user's personality type.
     * @return a List of personality types compatible with the given personality type.
     */
    public List<String> getCompatiblePersonalityTypes(String personalityType) {
        Map<String, List<String>> personalityMap = new HashMap<>();
        personalityMap.put("ESTP", List.of("ISTP", "ESFJ", "ISFJ", "ESTP", "ENFJ", "INFJ"));
        personalityMap.put("estp", List.of("ISTP", "ESFJ", "ISFJ", "ESTP", "ENFJ", "INFJ"));
        personalityMap.put("ISTP", List.of("ESTP", "ESFJ", "ISFJ", "ISTP", "ENFJ", "INFJ"));
        personalityMap.put("istp", List.of("ESTP", "ESFJ", "ISFJ", "ISTP", "ENFJ", "INFJ"));
        personalityMap.put("ESFP", List.of("ISFP", "ESTJ", "ISTJ", "ESFP", "ENTJ", "INFJ"));
        personalityMap.put("esfp", List.of("ISFP", "ESTJ", "ISTJ", "ESFP", "ENTJ", "INFJ"));
        personalityMap.put("ISFP", List.of("ESFP", "ESTJ", "ISTJ", "ISFP", "ENTJ", "INTJ"));
        personalityMap.put("isfp", List.of("ESFP", "ESTJ", "ISTJ", "ISFP", "ENTJ", "INTJ"));
        personalityMap.put("ESTJ", List.of("ESFP", "ISFP", "ISTJ", "ESTJ", "ENFP", "INFP"));
        personalityMap.put("estj", List.of("ESFP", "ISFP", "ISTJ", "ESTJ", "ENFP", "INFP"));
        personalityMap.put("ISTJ", List.of("ESFP", "ISFP", "ESTJ", "ISTJ", "ENFP", "INFP"));
        personalityMap.put("istj", List.of("ESFP", "ISFP", "ESTJ", "ISTJ", "ENFP", "INFP"));
        personalityMap.put("ESFJ", List.of("ESTP", "ISTP", "ISFJ", "ESFJ", "ENTP", "INTP"));
        personalityMap.put("esfj", List.of("ESTP", "ISTP", "ISFJ", "ESFJ", "ENTP", "INTP"));
        personalityMap.put("ISFJ", List.of("ESTP", "ISTP", "ESFJ", "ISFJ", "ENTP", "INTP"));
        personalityMap.put("isfj", List.of("ESTP", "ISTP", "ESFJ", "ISFJ", "ENTP", "INTP"));
        personalityMap.put("ENFP", List.of("INFP", "ENTJ", "INTJ", "ESTJ", "ISTJ", "ENFP"));
        personalityMap.put("enfp", List.of("INFP", "ENTJ", "INTJ", "ESTJ", "ISTJ", "ENFP"));
        personalityMap.put("INFP", List.of("ENFP", "ENTJ", "INTJ", "ESTJ", "ISTJ", "INFP"));
        personalityMap.put("infp", List.of("ENFP", "ENTJ", "INTJ", "ESTJ", "ISTJ", "INFP"));
        personalityMap.put("ENFJ", List.of("INFJ", "ENTP", "INTP", "ESTP", "ISTP", "ENFJ"));
        personalityMap.put("enfj", List.of("INFJ", "ENTP", "INTP", "ESTP", "ISTP", "ENFJ"));
        personalityMap.put("INFJ", List.of("ENFJ", "ENTP", "INTP", "ESTP", "ISTP", "INFJ"));
        personalityMap.put("infj", List.of("ENFJ", "ENTP", "INTP", "ESTP", "ISTP", "INFJ"));
        personalityMap.put("ENTP", List.of("ENFJ", "INFJ", "INTP", "ESFJ", "ISFJ", "ENTP"));
        personalityMap.put("entp", List.of("ENFJ", "INFJ", "INTP", "ESFJ", "ISFJ", "ENTP"));
        personalityMap.put("INTP", List.of("ENFJ", "INFJ", "ENTP", "ESFJ", "ISFJ", "INTP"));
        personalityMap.put("intp", List.of("ENFJ", "INFJ", "ENTP", "ESFJ", "ISFJ", "INTP"));
        personalityMap.put("ENTJ", List.of("ENFP", "INFP", "ENTJ", "ESFP", "ISFP", "ENTJ"));
        personalityMap.put("entj", List.of("ENFP", "INFP", "ENTJ", "ESFP", "ISFP", "ENTJ"));
        personalityMap.put("INTJ", List.of("ENFP", "INFP", "ENTJ", "ESFP", "ISFP", "INTJ"));
        personalityMap.put("intj", List.of("ENFP", "INFP", "ENTJ", "ESFP", "ISFP", "INTJ"));

        if (personalityType != null && personalityMap.containsKey(personalityType)) {
            return personalityMap.get(personalityType);
        }

        return null;
    }

    /**
     * Perform a sort on the list of connexions by their common hobbies.
     * @param currUserHobbies A list of the current user's hobbies.
     * @param connexions A list of user connexions.
     * @return a Map of compatible users in order from most compatible to least
     *
     */
    public Map<String, Integer> sortConnexions(List<String> currUserHobbies, List<User> connexions) {
        Map<String, Integer> connexionTreeMap = new TreeMap<>();

        for (User user : connexions) {
            if (user.getHobbies() != null && !user.getHobbies().isEmpty()) {
                int count = 0;
                List<String> connexionHobbies = user.getHobbies();

                for (String userHobby : currUserHobbies) {
                    if (connexionHobbies.contains(userHobby)) {
                        count++;
                    }
                }
                connexionTreeMap.put(user.getId(), count);
            } else {
                connexionTreeMap.put(user.getId(), 0);
            }
        }
        return connexionTreeMap;
    }
}



