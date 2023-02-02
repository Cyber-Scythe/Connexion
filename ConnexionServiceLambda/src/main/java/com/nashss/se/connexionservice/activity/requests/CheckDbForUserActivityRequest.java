package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateUserActivityRequest.Builder.class)
public class CheckDbForUserActivityRequest {

    private final String email;
    private final String name;

    private CheckDbForUserActivityRequest(String email, String name) {

        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        return "CheckDbForUserActivityRequest{" +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
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

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public CheckDbForUserActivityRequest build() {
            return new CheckDbForUserActivityRequest(email, name);
        }
    }
}

