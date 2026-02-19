package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeleteMessagesActivityRequest.Builder.class)
public class GetUserProfileActivityRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String id;


    private GetUserProfileActivityRequest(String firstName, String lastName, String email, String id) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public String getEmail() {

        return email;
    }

    public String getId() {

        return id;
    }

    @Override
    public String toString() {
        return "GetUserProfileActivityRequest{" +
                "firstName='" + firstName + '\'' +
                "lastName='" + lastName + '\'' +
                "email='" + email + '\'' +
                "id='" + id + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String id;


        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
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

        public GetUserProfileActivityRequest build() {

            return new GetUserProfileActivityRequest(firstName, lastName, email, id);
        }
    }
}
