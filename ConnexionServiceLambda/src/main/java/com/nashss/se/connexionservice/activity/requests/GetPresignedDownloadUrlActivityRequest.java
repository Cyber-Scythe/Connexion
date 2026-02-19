package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class GetPresignedDownloadUrlActivityRequest {
    private String id;

    public GetPresignedDownloadUrlActivityRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    @Override
    public String toString() {
        return "GetPresignedDownloadUrlActivityRequest{" +
                "id='" + id + '\'' + '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;

        public Builder withId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetPresignedDownloadUrlActivityRequest build() {
            return new GetPresignedDownloadUrlActivityRequest(userId);
        }
    }
}
