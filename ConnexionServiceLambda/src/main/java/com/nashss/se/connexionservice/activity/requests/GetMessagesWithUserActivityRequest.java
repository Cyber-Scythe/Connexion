package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetMessagesWithUserActivityRequest.Builder.class)
public class GetMessagesWithUserActivityRequest {
    private final String userId;
    private final String currUserEmail;
    private final String otherUserEmail;

    private GetMessagesWithUserActivityRequest(String userId, String currUserEmail, String otherUserEmail) {
        this.userId = userId;
        this.currUserEmail = currUserEmail;
        this.otherUserEmail = otherUserEmail;
    }
    public String getUserId() {
        return userId;
    }

    public String getCurrUserEmail() {
        return currUserEmail;
    }

    public String getOtherUserEmail() {
        return otherUserEmail;
    }

    @Override
    public String toString() {
        return "GetConnexionsActivityRequest{" +
                "userId='" + userId + '\'' +
                "currUserEmail='" + currUserEmail + '\'' +
                "otherUserEmail='" + otherUserEmail + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String id;
        private String currEmail;
        private String otherEmail;

        public Builder withUserId(String userId) {
            this.id = userId;
            return this;
        }

        public Builder withCurrUserEmail(String currUserEmail) {
            this.currEmail = currUserEmail;
            return this;
        }
        public Builder withOtherUserEmail(String otherUserEmail) {
            this.otherEmail = otherUserEmail;
            return this;
        }

        public GetMessagesWithUserActivityRequest build() {
            return new GetMessagesWithUserActivityRequest(id, currEmail, otherEmail);
        }
    }
}
