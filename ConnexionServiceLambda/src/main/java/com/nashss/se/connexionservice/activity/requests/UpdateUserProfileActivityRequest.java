package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nashss.se.connexionservice.dynamodb.models.User;

import java.util.List;

import static com.nashss.se.connexionservice.utils.CollectionUtils.copyToList;

@JsonDeserialize(builder = UpdateUserProfileActivityRequest.Builder.class)
public class UpdateUserProfileActivityRequest {
    private final String email;
    private final String name;
    private final String id;
    private final int age;
    private final String city;
    private final String state;
    private final String personalityType;
    private final List<String> hobbies;
    private final List<User> connexions;


    public UpdateUserProfileActivityRequest(String email,
                                              String name,
                                              String id,
                                              int age,
                                              String city,
                                              String state,
                                              String personalityType,
                                              List<String> hobbies,
                                              List<User> connexions) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.age = age;
        this.city = city;
        this.state = state;
        this.personalityType = personalityType;
        this.hobbies = hobbies;
        this.connexions = connexions;
    }

    public String getEmail() {
        return email;
    }
    public String getName() { return name; }
    public String getId() { return id; }
    public int getAge() { return age; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPersonalityType() { return personalityType; }
    public List<String> getHobbies() { return copyToList(hobbies); }
    public List<User> getConnexions() { return copyToList(connexions); }

    @Override
    public String toString() {
        return "UpdateUserProfileActivityRequest{" +
                "email='" + email + '\'' +
                "name='" + name + '\'' +
                "id='" + id + '\'' +
                "age='" + age + '\'' +
                "city='" + city + '\'' +
                "state='" + state + '\'' +
                "personalityType='" + personalityType + '\'' +
                "hobbies='" + hobbies + '\'' +
                "connexions='" + connexions + '\'' +
                '}';
    }


    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String email;
        private String name;
        private String id;
        private int age;
        private String city;
        private String state;
        private String personalityType;
        private List<String> hobbies;
        private List<User> connexions;

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
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

        public Builder withConnexions(List<User> connexions) {
            this.connexions = copyToList(connexions);
            return this;
        }


        public UpdateUserProfileActivityRequest build() {
            return new UpdateUserProfileActivityRequest(email, name, id, age, city, state,
                    personalityType, hobbies, connexions);
        }
    }
}
