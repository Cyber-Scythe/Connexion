package com.nashss.se.connexionservice.activity.requests;

public class GetMessagesWithUserActivityRequest {
    private final String userId;
    private final String currUserEmail;
    private final String otherUserEmail;

    private GetMessagesWithUserActivityRequest(String userId, String currUserEmail, String otherUserEmail) {
        this.userId = userId;
        this.currUserEmail = currUserEmail;
        this.otherUserEmail = otherUserEmail;
    }
    public String getUserId() { return userId;}

    public String getCurrUserEmail() { return currUserEmail; }
    public String getOtherUserEmail() { return otherUserEmail; }

    @Override
    public String toString() {
        return "GetConnexionsActivityRequest{" +
                "userId='" + userId + '\'' +
                "currUserEmail='" + currUserEmail + '\'' +
                "otherUserEmail='" + otherUserEmail + '\'' +
                '}';
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String userId;
        private String currUserEmail;
        private String otherUserEmail;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withCurrUserEmail(String currUserEmail) {
            this.currUserEmail = currUserEmail;
            return this;
        }
        public Builder withOtherUserEmail(String otherUserEmail) {
            this.otherUserEmail = otherUserEmail;
            return this;
        }

        public GetMessagesWithUserActivityRequest build() { return new GetMessagesWithUserActivityRequest(userId, currUserEmail, otherUserEmail);
        }
    }
}
