package com.nashss.se.connexionservice.activity.requests;

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

    public String getId() { return id; }

    @Override
    public String toString() {
        return "GetConnexionsActivityRequest{" +
                "personalityType='" + personalityType + '\'' +
                "id='" + id + '\'' +
                '}';
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String personalityType;
        private String id;

        public Builder withPersonalityType(String personalityType) {
            this.personalityType = personalityType;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public GetConnexionsActivityRequest build() { return new GetConnexionsActivityRequest(personalityType, id);
        }
    }
}
