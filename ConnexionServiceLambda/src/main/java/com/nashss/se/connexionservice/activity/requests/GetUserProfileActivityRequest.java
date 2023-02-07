package com.nashss.se.connexionservice.activity.requests;

public class GetUserProfileActivityRequest {
    private final String id;

    private GetUserProfileActivityRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetUserProfileActivityRequest{" +
                "id='" + id + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public GetUserProfileActivityRequest build() {
            return new GetUserProfileActivityRequest(id);
        }
    }
}
