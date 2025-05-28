package com.nashss.se.connexionservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.io.Serializable;

import java.util.List;
import java.util.Objects;

import static com.nashss.se.connexionservice.utils.CollectionUtils.copyToList;

/**
 * Represents a record in the users table.
 */
@DynamoDBTable(tableName = "users")
public class User implements Serializable {
    public static final String PERSONALITY_TYPE_INDEX = "PersonalityTypeIndex";
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private int birthMonth;
    private int birthDay;
    private int birthYear;
    private String city;
    private String state;
    private String country;
    private String personalityType;
    private String aboutMe;
    private List<String> hobbies;
    private List<String> connexions;

    /**
     * Empty constructor for Category POJO.
     */
    public User() {
    }

    /**
     * Constructor with parameters for User POJO.
     * @param id for user ID
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param gender the user's gender
     * @param email email address of user
     * @param birthMonth the user's birth month
     * @param birthDay the user's birth Day
     * @param birthYear the user's birth year
     * @param city city the user lives in
     * @param state state the user lives in
     * @param country country the user lives in
     * @param personalityType user's personality type
     * @param aboutMe user's About Me
     * @param hobbies list of user's hobbies
     * @param connexions List of user's connexions
     */
    public User(String id,
                String email,
                String firstName,
                String lastName,
                String gender,
                int birthMonth,
                int birthDay,
                int birthYear,
                String city,
                String state,
                String country,
                String personalityType,
                List<String> hobbies,
                String aboutMe,
                List<String> connexions) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.birthYear = birthYear;
        this.city = city;
        this.state = state;
        this.country = country;
        this.personalityType = personalityType;
        this.hobbies = hobbies;
        this.aboutMe = aboutMe;
        this.connexions = connexions;
    }

    @DynamoDBHashKey(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // "name" is a reserved word in DDB, so the attribute in the table is called "userName".
    @DynamoDBAttribute(attributeName = "userFirstName")
    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    @DynamoDBAttribute(attributeName = "userLastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    @DynamoDBAttribute(attributeName = "birthMonth")
    public int getBirthMonth() {

        return birthMonth;
    }

    public void setBirthMonth(int birthMonth) {

        this.birthMonth = birthMonth;
    }

    @DynamoDBAttribute(attributeName = "birthDay")
    public int getBirthDay() {

        return birthDay;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    @DynamoDBAttribute(attributeName = "birthYear")
    public int getBirthYear() {

        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @DynamoDBAttribute(attributeName = "gender")
    public String getGender() {

        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    @DynamoDBAttribute(attributeName = "country")
    public String getCountry() {

        return country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = PERSONALITY_TYPE_INDEX,
            attributeName = "personalityType")
    public String getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(String personalityType) {
        this.personalityType = personalityType;
    }

    @DynamoDBAttribute(attributeName = "aboutMe")
    public String getAboutMe() {
        return aboutMe;
    }
    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    /**
     * Returns the set of hobbies associated with this User, null if there are none.
     *
     * @return List of hobbies for this user
     */
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
     * @param hobbies List of hobbies for this user
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
     * Returns the list of connexions associated with this User, null if there are none.
     *
     * @return List of connexions for this user
     */
    @DynamoDBAttribute(attributeName = "connexions")
    public List<String> getConnexions() {
        // normally, we would prefer to return an empty Set if there are no
        // tags, but DynamoDB doesn't represent empty Sets...needs to be null
        // instead
        if (null == connexions) {
            return null;
        }

        return connexions;
    }

    /**
     * Sets the connexions for this User as a copy of input, or null if input is null.
     *
     * @param connexions List of String connexions for this user
     */
    public void setConnexions(List<String> connexions) {
        if (null == connexions) {
            this.connexions = null;
        } else {
            this.connexions = copyToList(connexions);
        }

        this.connexions = copyToList(connexions);
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
                email.equals(user.email) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                gender.equals(user.gender) &&
                birthMonth == user.birthMonth &&
                birthDay == user.birthDay &&
                birthYear == user.birthYear &&
                city.equals(user.city) &&
                state.equals(user.state) &&
                country.equals(user.country) &&
                personalityType.equals(user.personalityType) &&
                Objects.equals(hobbies, user.hobbies) &&
                aboutMe.equals(user.aboutMe) &&
                Objects.equals(connexions, user.connexions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, gender, birthMonth, birthDay, birthYear, city, state,
                            country, personalityType, hobbies, aboutMe, connexions);
    }
}
