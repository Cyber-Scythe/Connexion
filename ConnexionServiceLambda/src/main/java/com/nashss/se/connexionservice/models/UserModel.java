package com.nashss.se.connexionservice.models;

import java.util.List;
import java.util.Objects;

import static com.nashss.se.connexionservice.utils.CollectionUtils.copyToList;

public class UserModel {
    private final String name;
    private final String email;
    private final String id;
    private final int age;
    private final String city;
    private final String state;
    private final String personalityType;
    private final List<String> hobbies;
    private final List<String> connexions;

    /**
     * Constructor for UserModel object.
     * <p>
     * @param name The name of the user
     * @param email The email of the user
     * @param id The id of the user
     * @param age The user's age
     * @param city The city the user lives in
     * @param state The state the user lives in
     * @param personalityType The personality type of the user
     * @param hobbies A list of user hobbies
     * @param connexions A list of the user's connexions
     */
    public UserModel(String name,
                      String email,
                      String id,
                      int age,
                      String city,
                      String state,
                      String personalityType,
                      List<String> hobbies,
                      List<String> connexions) {

        this.name = name;
        this.email = email;
        this.id = id;
        this.age = age;
        this.city = city;
        this.state = state;
        this.personalityType = personalityType;
        this.hobbies = hobbies;
        this.connexions = connexions;
    }

    public String getName() {

        return name;
    }

    public String getEmail() {

        return email;
    }

    public String getId() {
        return id;
    }
    public int getAge() {
        return age;
    }
    public String getCity() {

        return city;
    }

    public String getState() {

        return state;
    }
    public String getPersonalityType() {
        return personalityType;
    }

    public List<String> getHobbies() {

        return copyToList(hobbies);
    }
    public List<String> getConnexions() {
        return copyToList(connexions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserModel that = (UserModel) o;

        return Objects.equals(name, that.name) &&
                Objects.equals(email, that.email) &&
                Objects.equals(id, that.id) &&
                Objects.equals(age, that.age) &&
                Objects.equals(city, that.city) &&
                Objects.equals(state, that.state) &&
                Objects.equals(personalityType, that.personalityType) &&
                Objects.equals(hobbies, that.hobbies) &&
                Objects.equals(connexions, that.connexions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, id, age, city, state, personalityType, hobbies, connexions);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    public String getUserName() {

        return name;
    }
    public String getUserEmail() {
        return email;
    }
    public int getUserAge() {
        return age;
    }
    public String getUserId() {
        return id;
    }
    public String getUserCity() {
        return city;
    }
    public String getUserState() {
        return state;
    }
    public String getUserPersonalityType() {
        return personalityType;
    }
    public List<String> getUserHobbies() {
        return hobbies;
    }
    public List<String> getUserConnexions() {
        return connexions;
    }

    public static class Builder {
        private String name;
        private String email;
        private String id;
        private int age;
        private String city;
        private String state;
        private String personalityType;
        private List<String> hobbies;
        private List<String> connexions;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withAge(int age) {
            this.age = age;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withState(String state) {
            this.state = state;
            return this;
        }

        public Builder withPersonalityType(String personalityType) {
            this.personalityType = personalityType;
            return this;
        }

        public Builder withHobbies(List<String> hobbies) {
            this.hobbies = copyToList(hobbies);
            return this;
        }

        public Builder withConnexions(List<String> connexions) {

            this.connexions = copyToList(connexions);
            return this;
        }

        public UserModel build() {
            return new UserModel(name,
                                email,
                                id,
                                age,
                                city,
                                state,
                                personalityType,
                                hobbies,
                                connexions);
        }
    }
}
