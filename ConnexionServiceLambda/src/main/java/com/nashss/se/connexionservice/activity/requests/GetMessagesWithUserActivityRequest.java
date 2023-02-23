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

    /**
     * Constructor for builder that returns a new Builder object.
     * @return Returns a new Builder object
     */
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String id;
        private String currEmail;
        private String otherEmail;

        /**
         * With user ID attribute.
         * @param userId the user's ID.
         * @return returns Builder object with user's ID
         */
        public Builder withUserId(String userId) {
            this.id = userId;
            return this;
        }

        /**
         * With current user's email attribute.
         * @param currUserEmail the current user's email.
         * @return returns Builder object with current user's email.
         */
        public Builder withCurrUserEmail(String currUserEmail) {
            this.currEmail = currUserEmail;
            return this;
        }

        /**
         * With otherUserEmail attribute.
         * @param otherUserEmail the other user's email.
         * @return returns Builder Object with other user's email
         */
        public Builder withOtherUserEmail(String otherUserEmail) {
            this.otherEmail = otherUserEmail;
            return this;
        }

        /**
         * Builds the GetMessagesWithUserActivityRequest request.
         * @return returns a new GetMessagesWithUserActivityRequest with user ID,
         *         email, and other user email.
         */
        public GetMessagesWithUserActivityRequest build() {
            return new GetMessagesWithUserActivityRequest(id, currEmail, otherEmail);
        }
    }
}
