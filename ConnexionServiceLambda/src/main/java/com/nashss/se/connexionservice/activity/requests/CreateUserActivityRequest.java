package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

import static com.nashss.se.connexionservice.utils.CollectionUtils.copyToList;

@JsonDeserialize(builder = CreateUserActivityRequest.Builder.class)
public class CreateUserActivityRequest {
    private final String name;
    private final String email;

    private final String city;
    private final String state;
    private final String personalityType;
    private final List<String> hobbies;
    private final List<String> connections;

    private CreateUserActivityRequest(String name,
                                      String email,
                                      String city,
                                      String state,
                                      String personalityType,
                                      List<String> hobbies,
                                      List<String> connections) {

        this.name = name;
        this.email = email;
        this.city = city;
        this.state = state;
        this.personalityType = personalityType;
        this.hobbies = hobbies;
        this.connections = connections;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }
    public String getState() { return state; }
    public String getPersonalityType() { return personalityType; }

    public List<String> getHobbies() {
        return copyToList(hobbies);
    }
    public List<String> getConnections() { return copyToList(connections); }

    @Override
    public String toString() {
        return "CreateUserActivityRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", personalityType='" + personalityType + '\'' +
                ", hobbies=" + hobbies + '\'' +
                ", connections=" + connections +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String name;
        private String email;
        private String birthdate;
        private String city;
        private String state;
        private String personalityType;
        private List<String> hobbies;
        private List<String> connections;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withBirthdate(String birthdate) {
            this.birthdate = birthdate;
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

        public Builder withConnections(List<String> connections) {
            this.connections = copyToList(connections);
            return this;
        }


        public CreateUserActivityRequest build() {
            return new CreateUserActivityRequest(name, email, city, state, personalityType, hobbies, connections);
        }
    }
}
