package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = DeleteMessagesActivityRequest.Builder.class)
public class DeleteMessagesActivityRequest {

    private final String senderEmail;
    private final String recipientEmail;
    private final String dateTimeSent;


    private DeleteMessagesActivityRequest(String senderEmail, String recipientEmail, String dateTimeSent) {

        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
        this.dateTimeSent = dateTimeSent;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getRecipientEmail() { return recipientEmail; }
    public String getDateTimeSent() { return dateTimeSent; }

    @Override
    public String toString() {
        return "DeleteMessagesActivityRequest{ " +
                "senderEmail='" + senderEmail + '\'' +
                ", recipientEmail='" + recipientEmail + '\'' +
                ", dateTimeSent='" + dateTimeSent + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String senderEmail;
        private String recipientEmail;
        private String dateTimeSent;

        public Builder withSenderEmail(String senderEmail) {
            this.senderEmail = senderEmail;
            return this;
        }

        public Builder withRecipientEmail(String recipientEmail) {
            this.recipientEmail = recipientEmail;
            return this;
        }

        public Builder withDateTimeSent(String dateTimeSent) {
            this.dateTimeSent = dateTimeSent;
            return this;
        }

        public DeleteMessagesActivityRequest build() {
            return new DeleteMessagesActivityRequest(senderEmail, recipientEmail, dateTimeSent);
        }
    }
}

