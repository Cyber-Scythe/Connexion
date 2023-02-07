package com.nashss.se.connexionservice.activity.requests;

import java.util.List;

public class UpdateUserProfileActivityRequest {
    private final String email;
    private final String name;
    private final String id;
    private final String birthdate;
    private final String city;
    private final String state;
    private final String personalityType;
    private final List<String> hobbies;
    private final List<String> connections;

    private UpdateUserProfileActivityRequest(String email,
                                              String name,
                                              String id,
                                              String birthdate,
                                              String city,
                                              String state,
                                              String personalityType,
                                              List<String> hobbies,
                                              List<String> connections) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.birthdate = birthdate;
        this.city = city;
        this.state = state;
        this.personalityType = personalityType;
        this.hobbies = hobbies;
        this.connections = connections;
    }

    public String getEmail() {
        return email;
    }
    public String getName() { return name; }
    public String getId() { return id; }
    public String getBirthdate() { return birthdate; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPersonalityType() { return personalityType; }
    public List<String> getHobbies() { return List.copyOf(hobbies); }
    public List<String> getConnections() { return List.copyOf(connections); }

    @Override
    public String toString() {
        return "UpdateUserProfileActivityRequest{" +
                "email='" + email + '\'' +
                "name='" + name + '\'' +
                "id='" + id + '\'' +
                "birthdate='" + birthdate + '\'' +
                "city='" + city + '\'' +
                "state='" + state + '\'' +
                "personalityType='" + personalityType + '\'' +
                "hobbies='" + hobbies + '\'' +
                "connections='" + connections + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String email;
        private String name;
        private String id;
        private String birthdate;
        private String city;
        private String state;
        private String personalityType;
        private List<String> hobbies;
        private List<String> connections;

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
            this.hobbies = hobbies;
            return this;
        }

        public Builder withConnections(List<String> connections) {
            this.connections = connections;
            return this;
        }


        public UpdateUserProfileActivityRequest build() {
            return new UpdateUserProfileActivityRequest(email, name, id, birthdate, city, state,
                    personalityType, hobbies, connections);
        }
    }
}
