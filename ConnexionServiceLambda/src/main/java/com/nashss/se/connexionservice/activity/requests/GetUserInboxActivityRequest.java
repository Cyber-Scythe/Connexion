package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetUserInboxActivityRequest.Builder.class)
public class GetUserInboxActivityRequest {
    private final String userId;
    private final String currUserEmail;

    /**
     * Constructor for GetUserInboxActivityRequest.
     * @param userId The userId of the request
     * @param currUserEmail The current user's email for the request
     */
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

    /**
     * Constructor for a new Builder object.
     * @return new Builder object
     */
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String id;
        private String currEmail;

        /**
         * With user ID attribute.
         * @param userId The user's ID
         * @return Builder with the user's ID.
         */
        public Builder withUserId(String userId) {
            this.id = userId;
            return this;
        }

        /**
         * With current user's email attribute.
         * @param currUserEmail current user's email
         * @return Builder with current user's email
         */
        public Builder withCurrUserEmail(String currUserEmail) {
            this.currEmail = currUserEmail;
            return this;
        }

        /**
         * Builds the GetUserInboxRequest request.
         * @return returns a new GetUserInboxActivityRequest
         */
        public GetUserInboxActivityRequest build() {
            return new GetUserInboxActivityRequest(id, currEmail);
        }
    }
}
