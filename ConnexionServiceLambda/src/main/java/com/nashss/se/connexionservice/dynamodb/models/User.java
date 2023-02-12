package com.nashss.se.connexionservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.nashss.se.connexionservice.converters.StringConverter;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.nashss.se.connexionservice.utils.CollectionUtils.copyToList;
import static com.nashss.se.connexionservice.utils.CollectionUtils.copyToSet;

/**
 * Represents a record in the users table.
 */
@DynamoDBTable(tableName = "users")
public class User implements Serializable {
    private String id;
    private String name;
    private String email;
    private int age;
    private String city;
    private String state;
    private String personalityType;
    private List<String> hobbies;
    private List<String> connections;

    /**
     * Empty constructor for Category POJO.
     */
    public User() {
    }

    /**
     * Constructor with parameters for User POJO.
     * @param id for user ID
     * @param name the user's name
     * @param email email address of user
     * @param age user's age
     * @param city city the user lives in
     * @param state state the user lives in
     * @param personalityType user's personality type
     * @param hobbies list of user's hobbies
     * @param connections list of user's connections
     */
    public User(String id,
                String name,
                String email,
                int age,
                String city,
                String state,
                String personalityType,
                List<String> hobbies,
                List<String> connections) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.city = city;
        this.state = state;
        this.personalityType = personalityType;
        this.hobbies = hobbies;
        this.connections = connections;
    }
    @DynamoDBHashKey(attributeName = "id")
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    // "name" is a reserved word in DDB, so the attribute in the table is called "userName".
    @DynamoDBAttribute(attributeName = "userName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBAttribute(attributeName = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @DynamoDBAttribute(attributeName = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @DynamoDBAttribute(attributeName = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @DynamoDBAttribute(attributeName = "personalityType")
    public String getPersonalityType() { return personalityType; }

    public void setPersonalityType(String personalityType) { this.personalityType = personalityType; }

    /**
     * Returns the set of hobbies associated with this User, null if there are none.
     *
     * @return Set of hobbies for this user
     */
    //@DynamoDBTypeConverted(converter = StringConverter.class)
    @DynamoDBAttribute(attributeName = "hobbies")
    public List<String> getHobbies() {
        // normally, we would prefer to return an empty Set if there are no
        // hobbies, but DynamoDB doesn't represent empty Sets...needs to be null
        // instead
        if (null == hobbies) {
            return null;
        }

        return copyToList(hobbies);
    }

    /**
     * Sets the hobbies for this User as a copy of input, or null if input is null.
     *
     * @param hobbies Set of hobbies for this user
     */
    public void setHobbies(List<String> hobbies) {

        if (null == hobbies) {
            this.hobbies = null;
        } else {
            this.hobbies = copyToList(hobbies);
        }

        this.hobbies = copyToList(hobbies);
    }

    /**
     * Returns the set of connections associated with this User, null if there are none.
     *
     * @return Set of connections for this user
     */
    //@DynamoDBTypeConverted(converter = StringConverter.class)
    @DynamoDBAttribute(attributeName = "connections")
    public List<String> getConnections() {
        // normally, we would prefer to return an empty Set if there are no
        // tags, but DynamoDB doesn't represent empty Sets...needs to be null
        // instead
        if (null == connections) {
            return null;
        }

        return copyToList(connections);
    }

    /**
     * Sets the connections for this User as a copy of input, or null if input is null.
     *
     * @param connections Set of connections for this playlist
     */
    public void setConnections(List<String> connections) {
        // see comment in getTags()
        if (null == connections) {
            this.connections = null;
        } else {
            this.connections = copyToList(connections);
        }

        this.connections = copyToList(connections);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;
        return id.equals(user.id) &&
                name.equals(user.name) &&
                email.equals(user.email) &&
                age == user.age &&
                city.equals(user.city) &&
                state.equals(user.state) &&
                Objects.equals(hobbies, user.hobbies) &&
                Objects.equals(connections, user.connections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age, city, state,
                            personalityType, hobbies, connections);
    }

}