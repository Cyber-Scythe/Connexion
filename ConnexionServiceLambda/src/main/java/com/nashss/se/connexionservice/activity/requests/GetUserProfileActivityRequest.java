package com.nashss.se.connexionservice.activity.requests;

public class GetUserProfileActivityRequest {
    private final String name;
    private final String email;
    private final String id;


    private GetUserProfileActivityRequest(String name, String email, String id) {

        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getName() { return name; }

    public String getEmail() { return email; }
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetUserProfileActivityRequest{" +
                "name='" + name + '\'' +
                "email='" + email + '\'' +
                "id='" + id + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String email;
        private String id;


        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public GetUserProfileActivityRequest build() {
            return new GetUserProfileActivityRequest(name, email, id);
        }
    }
}
