package com.nashss.se.connexionservice.activity.requests;

public class GetUserInboxActivityRequest {
    private final String userId;
    private final String currUserEmail;


    private GetUserInboxActivityRequest(String userId, String currUserEmail) {

        this.userId = userId;
        this.currUserEmail = currUserEmail;
    }

    public String getUserId() { return userId; }
    public String getCurrUserEmail() { return currUserEmail; }


    @Override
    public String toString() {
        return "GetConnexionsActivityRequest{" +
                "userId='" + userId + '\'' +
                "currUserEmail='" + currUserEmail + '\'' +
                '}';
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String userId;
        private String currUserEmail;


        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public Builder withCurrUserEmail(String currUserEmail) {
            this.currUserEmail = currUserEmail;
            return this;
        }


        public GetUserInboxActivityRequest build() { return new GetUserInboxActivityRequest(userId, currUserEmail);
        }
    }
}