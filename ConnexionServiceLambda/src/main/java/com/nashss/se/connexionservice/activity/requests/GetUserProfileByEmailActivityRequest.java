package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetUserProfileByEmailActivityRequest.Builder.class)
public class GetUserProfileByEmailActivityRequest {
    private final String userEmail;

    private GetUserProfileByEmailActivityRequest(String userEmail) {

        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }


    @Override
    public String toString() {
        return "GetUserProfileByEmailActivityRequest{" +
                "userEmail='" + userEmail + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    @JsonPOJOBuilder
    public static class Builder {
        private String userEmail;

        public Builder withUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public GetUserProfileByEmailActivityRequest build() {
            return new GetUserProfileByEmailActivityRequest(userEmail);
        }
    }
}
