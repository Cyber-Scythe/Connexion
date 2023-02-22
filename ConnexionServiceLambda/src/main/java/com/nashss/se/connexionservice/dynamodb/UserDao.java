package com.nashss.se.connexionservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.metrics.MetricsConstants;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

import static com.nashss.se.connexionservice.utils.CollectionUtils.copyToList;


/**
 * Accesses data for a user using {@link User} to represent the model in DynamoDB.
 */
@Singleton
public class UserDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;
    private final HashMap<Integer, String> connexionMap = new HashMap<>();

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

    /**
     * Returns the user corresponding to the id
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
     */
    public User saveUser(User user) {
        this.dynamoDbMapper.save(user);
        return user;
    }


    /**
     * Perform a search ("via a scan") of the users table
     * @return a List of all User objects in the table
     */
     public List<String> getAllConnexions(User currUser) {
         DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
         PaginatedScanList<User> scanResult = dynamoDbMapper.scan(User.class, scanExpression);

         System.out.println("scanResult: " + scanResult);

         if (!currUser.getHobbies().isEmpty()) {
             Map<String, Integer> sortedMap = connexionsSort(currUser.getHobbies(), scanResult);
             Map<String, Integer> connexionMap = new HashMap<>(sortedMap);

             List<String> sortedConnexions = copyToList(connexionMap.keySet());

             sortedConnexions.remove(currUser.getId());
             return sortedConnexions;
         } else {
             List<String> connexionIds = new ArrayList<>();
             for (int i = 0; i < scanResult.size(); i++) {
                 connexionIds.add(scanResult.get(i).getId());
             }
             return connexionIds;
         }
     }


    /**
     * Perform a search (via a "scan") of the users table for users matching the given criteria.
     * "personalityType" attribute is searched.
     * @return a List of User objects that match the search criteria.
     */
    public List<String> getConnexions(String currUserId, List<String> personalityTypes) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        Map<String, Integer> connexionMap = new HashMap<>();

        User currUser = getUser(currUserId);


        if (personalityTypes.isEmpty()) {
             return getAllConnexions(currUser);
        }

        for (int i = 0; i < personalityTypes.size(); i++) {
            valueMap.put(":personalityType" + i, new AttributeValue().withS(personalityTypes.get(i)));
        }

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("personalityType = :personalityType0 OR personalityType = :personalityType1 " +
                        "OR personalityType = :personalityType2 OR personalityType = :personalityType3 OR personalityType " +
                        "= :personalityType4 OR personalityType = :personalityType5")
                .withExpressionAttributeValues(valueMap);
        PaginatedScanList<User> connexionsList = dynamoDbMapper.scan(User.class, scanExpression);

        if (currUser.getHobbies() != null && !currUser.getHobbies().isEmpty()) {
            connexionMap = connexionsSort(currUser.getHobbies(), connexionsList);

            List<String> sortedConnexions = copyToList(connexionMap.keySet());
            Collections.reverse(sortedConnexions);

            return sortedConnexions;
        } else {
            List<String> connexionIds = new ArrayList<>();
            for (User user : connexionsList) {
                connexionIds.add(user.getId());
            }

            return connexionIds;
        }
    }


    /**
     * Return a list of compatible personality types for the given personality type
     * @return a List of personality types compatible with the given personality type.
     */
    public List<String> getCompatiblePersonalityTypes(String personalityType) {

        if (personalityType != null) {
            switch (personalityType) {
                case "ESTP":
                    return List.of("ISTP", "ESFJ", "ISFJ", "ESTP", "ENFJ", "INFJ");
                case "ISTP":
                    return List.of("ESTP", "ESFJ", "ISFJ", "ISTP", "ENFJ", "INFJ");
                case "ESFP":
                    return List.of("ISFP", "ESTJ", "ISTJ", "ESFP", "ENTJ", "INFJ");
                case "ISFP":
                    return List.of("ESFP", "ESTJ", "ISTJ", "ISFP", "ENTJ", "INTJ");
                case "ESTJ":
                    return List.of("ESFP", "ISFP", "ISTJ", "ESTJ", "ENFP", "INFP");
                case "ISTJ":
                    return List.of("ESFP", "ISFP", "ESTJ", "ISTJ", "ENFP", "INFP");
                case "ESFJ":
                    return List.of("ESTP", "ISTP", "ISFJ", "ESFJ", "ENTP", "INTP");
                case "ISFJ":
                    return List.of("ESTP", "ISTP", "ESFJ", "ISFJ", "ENTP", "INTP");
                case "ENFP":
                    return List.of("INFP", "ENTJ", "INTJ", "ESTJ", "ISTJ", "ENFP");
                case "INFP":
                    return List.of("ENFP", "ENTJ", "INTJ", "ESTJ", "ISTJ", "INFP");
                case "ENFJ":
                    return List.of("INFJ", "ENTP", "INTP", "ESTP", "ISTP", "ENFJ");
                case "INFJ":
                    return List.of("ENFJ", "ENTP", "INTP", "ESTP", "ISTP", "INFJ");
                case "ENTP":
                    return List.of("ENFJ", "INFJ", "INTP", "ESFJ", "ISFJ", "ENTP");
                case "INTP":
                    return List.of("ENFJ", "INFJ", "ENTP", "ESFJ", "ISFJ", "INTP");
                case "ENTJ":
                    return List.of("ENFP", "INFP", "ENTJ", "ESFP", "ISFP", "ENTJ");
                case "INTJ":
                    return List.of("ENFP", "INFP", "ENTJ", "ESFP", "ISFP", "INTJ");
            }
        }

        return null;
    }

    /**
     * Perform a sort on the list of connexions by their common hobbies
     * @return a Map of compatible users in order from most compatible to least
     */
    public Map<String, Integer> connexionsSort(List<String> currUserHobbies, List<User> connexion) {
        Map<String, Integer> connexionTreeMap = new TreeMap<>();

        if (!currUserHobbies.isEmpty()) {
            for (User user : connexion) {
                if(user.getHobbies() != null) {
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
        }
        return connexionTreeMap;
    }
}

