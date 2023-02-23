package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetConnexionsActivityRequest.Builder.class)
public class GetConnexionsActivityRequest {
    private final String personalityType;
    private final String id;

    private GetConnexionsActivityRequest(String personalityType, String id) {

        this.personalityType = personalityType;
        this.id = id;
    }

    public String getPersonalityType() {

        return personalityType;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetConnexionsActivityRequest{" +
                "personalityType='" + personalityType + '\'' +
                "id='" + id + '\'' +
                '}';
    }

    /**
     * Constructor the creates a new Builder object.
     * @return New Builder object.
     */
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userPersonalityType;
        private String userId;

        /**
         * With personality type attribute.
         * @param personalityType The user's personality type.
         * @return returns Builder object with personality type
         */
        public Builder withPersonalityType(String personalityType) {
            this.userPersonalityType = personalityType;
            return this;
        }

        /**
         * With ID attribute.
         * @param id the user's ID
         * @return returns Builder object with user's ID
         */
        public Builder withId(String id) {
            this.userId = id;
            return this;
        }

        /**
         * Builds a new GerConnexionsActivityRequest.
         * @return returns a new GetConnexionsActivityRequest with user's personality
         *         type and user ID.
         */
        public GetConnexionsActivityRequest build() {
            return new GetConnexionsActivityRequest(userPersonalityType, userId);
        }
    }
}
