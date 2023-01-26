package com.nashss.se.connexionservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

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
public class User {
    private String name;
    private String email;
    private String birthdate;
    private String city;
    private String state;
    private String personalityType;
    private Set<String> hobbies;
    private Set<String> connections;

    @DynamoDBHashKey(attributeName = "userName")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // "name" is a reserved word in DDB, so the attribute in the table is called "playlistName".
    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBAttribute(attributeName = "birthdate")
    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
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
    @DynamoDBAttribute(attributeName = "hobbies")
    public Set<String> getHobbies() {
        // normally, we would prefer to return an empty Set if there are no
        // tags, but DynamoDB doesn't represent empty Sets...needs to be null
        // instead
        if (null == hobbies) {
            return null;
        }

        return new HashSet<>(hobbies);
    }

    /**
     * Sets the hobbies for this User as a copy of input, or null if input is null.
     *
     * @param hobbies Set of hobbies for this user
     */
    public void setHobbies(Set<String> hobbies) {
        // see comment in getTags()
        if (null == hobbies) {
            this.hobbies = null;
        } else {
            this.hobbies = new HashSet<>(hobbies);
        }

        this.hobbies = copyToSet(hobbies);
    }

    /**
     * Returns the set of connections associated with this User, null if there are none.
     *
     * @return Set of connections for this user
     */
    @DynamoDBAttribute(attributeName = "connections")
    public Set<String> getConnections() {
        // normally, we would prefer to return an empty Set if there are no
        // tags, but DynamoDB doesn't represent empty Sets...needs to be null
        // instead
        if (null == connections) {
            return null;
        }

        return new HashSet<>(connections);
    }

    /**
     * Sets the connections for this User as a copy of input, or null if input is null.
     *
     * @param connections Set of connections for this playlist
     */
    public void setConnections(Set<String> connections) {
        // see comment in getTags()
        if (null == connections) {
            this.connections = null;
        } else {
            this.connections = new HashSet<>(connections);
        }

        this.connections = copyToSet(connections);
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
        return name.equals(user.name) &&
                email.equals(user.email) &&
                birthdate.equals(user.birthdate) &&
                city.equals(user.city) &&
                state.equals(user.state) &&
                Objects.equals(hobbies, user.hobbies) &&
                Objects.equals(connections, user.connections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, birthdate, city, state,
                            personalityType, hobbies, connections);
    }

}