package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CheckDbForUserActivityRequest.Builder.class)
public class CheckDbForUserActivityRequest {

    private final String email;
    private final String name;
    private final String id;

    private CheckDbForUserActivityRequest(String email, String name, String id) {

        this.email = email;
        this.name = name;
        this.id = id;
    }

    public String getEmail() {

        return email;
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CheckDbForUserActivityRequest{" +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
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


        public CheckDbForUserActivityRequest build() {

            return new CheckDbForUserActivityRequest(email, name, id);
        }
    }
}

