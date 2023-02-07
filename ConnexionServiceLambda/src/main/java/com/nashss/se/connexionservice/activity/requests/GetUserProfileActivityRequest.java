package com.nashss.se.connexionservice.activity.requests;

public class GetUserProfileActivityRequest {

    private final String email;
    private final String name;
    private final String id;


    private GetUserProfileActivityRequest(String email, String name, String id) {
        this.email = email;
        this.name = name;
        this.id = id;
    }

    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetUserProfileActivityRequest{" +
                "email='" + email + '\'' +
                "name='" + name + '\'' +
                "id='" + id + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String email;
        private String name;
        private String id;


        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public GetUserProfileActivityRequest build() {
            return new GetUserProfileActivityRequest(email, name, id);
        }
    }
}
