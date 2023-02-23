package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetUserInboxActivityRequest.Builder.class)
public class GetUserInboxActivityRequest {
    private final String userId;
    private final String currUserEmail;


    private GetUserInboxActivityRequest(String userId, String currUserEmail) {

        this.userId = userId;
        this.currUserEmail = currUserEmail;
    }

    public String getUserId() {
        return userId; }

    public String getCurrUserEmail() {
        return currUserEmail; }


    @Override
    public String toString() {
        return "GetConnexionsActivityRequest{" +
                "userId='" + userId + '\'' +
                "currUserEmail='" + currUserEmail + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String id;
        private String currEmail;


        public Builder withUserId(String userId) {
            this.id = userId;
            return this;
        }
        public Builder withCurrUserEmail(String currUserEmail) {
            this.currEmail = currUserEmail;
            return this;
        }


        public GetUserInboxActivityRequest build() {

            return new GetUserInboxActivityRequest(id, currEmail);
        }
    }
}
