package com.nashss.se.connexionservice.activity.requests;

public class GetConnexionProfileActivityRequest {
    private final String id;


    private GetConnexionProfileActivityRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetConnexionProfileActivityRequest{" +
                "id='" + id + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public GetConnexionProfileActivityRequest build() {
            return new GetConnexionProfileActivityRequest(id);
        }
    }
}
