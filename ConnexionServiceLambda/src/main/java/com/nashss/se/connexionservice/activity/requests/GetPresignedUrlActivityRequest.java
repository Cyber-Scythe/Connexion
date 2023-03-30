package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GetPresignedUrlActivityRequest.Builder.class)
public class GetPresignedUrlActivityRequest {

    private String id;
    private String url;

    public GetPresignedUrlActivityRequest(String id,
                                          String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "UpdateUserPhotoActivityRequest{" +
                "id='" + id + '\'' + '}' +
                "url=" + url;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {

        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String presignedUrl;

        public Builder withId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withUrl(String presignedUrl) {
            this.presignedUrl = presignedUrl;
            return this;
        }


        public GetPresignedUrlActivityRequest build() {
            return new GetPresignedUrlActivityRequest(userId, presignedUrl);
        }
    }
}
