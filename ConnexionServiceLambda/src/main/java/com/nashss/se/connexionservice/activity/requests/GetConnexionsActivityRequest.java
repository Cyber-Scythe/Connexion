package com.nashss.se.connexionservice.activity.requests;

public class GetConnexionsActivityRequest {
    private final String id;
    private final String personalityType;
    private GetConnexionsActivityRequest(String id, String personalityType) {
        this.id = id;
        this.personalityType = personalityType;
    }

    public String getId() {
        return id;
    }

    public String getPersonalityType() {
        return personalityType;
    }

    @Override
    public String toString() {
        return "GetConnexionsActivityRequest{" +
                "id='" + id + '\'' +
                "personalityType='" + personalityType + '\'' +
                '}';
    }


    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String id;
        private String personalityType;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withPersonalityType(String personalityType) {
            this.personalityType = personalityType;
            return this;
        }

        public GetConnexionsActivityRequest build() { return new GetConnexionsActivityRequest(id, personalityType);
        }
    }
}
