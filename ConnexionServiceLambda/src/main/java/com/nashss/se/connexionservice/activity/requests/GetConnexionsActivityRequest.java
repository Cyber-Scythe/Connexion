package com.nashss.se.connexionservice.activity.requests;

public class GetConnexionsActivityRequest {
    private final String personalityType;
    private GetConnexionsActivityRequest(String personalityType) {
        this.personalityType = personalityType;
    }

    public String getPersonalityType() {
        return personalityType;
    }

    @Override
    public String toString() {
        return "GetConnexionsActivityRequest{" +
                "personalityType='" + personalityType + '\'' +
                '}';
    }


    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String personalityType;

        public Builder withPersonalityType(String personalityType) {
            this.personalityType = personalityType;
            return this;
        }

        public GetConnexionsActivityRequest build() { return new GetConnexionsActivityRequest(personalityType);
        }
    }
}
