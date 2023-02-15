package com.nashss.se.connexionservice.activity.requests;

public class GetUserInboxActivityRequest {
    private final String userId;
    private final String senderEmail;
    private final String recipientEmail;

    private GetUserInboxActivityRequest(String userId, String senderEmail,
                                        String recipientEmail) {

        this.userId = userId;
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
    }

    public String getUserId() { return userId; }
    public String getSenderEmail() { return senderEmail; }
    public String getRecipientEmail() { return recipientEmail; }

    @Override
    public String toString() {
        return "GetConnexionsActivityRequest{" +
                "userId='" + userId + '\'' +
                "senderEmail='" + senderEmail + '\'' +
                "recipientEmail='" + recipientEmail + '\'' +
                '}';
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String userId;
        private String senderEmail;
        private String recipientEmail;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public Builder withSenderEmail(String senderEmail) {
            this.senderEmail = senderEmail;
            return this;
        }

        public Builder withRecipientEmail(String recipientEmail) {
            this.recipientEmail = recipientEmail;
            return this;
        }

        public GetUserInboxActivityRequest build() { return new GetUserInboxActivityRequest(userId, senderEmail, recipientEmail);
        }
    }
}
