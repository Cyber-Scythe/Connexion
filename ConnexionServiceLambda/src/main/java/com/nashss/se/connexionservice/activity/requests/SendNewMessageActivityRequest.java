package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = SendNewMessageActivityRequest.Builder.class)
public class SendNewMessageActivityRequest {
    private final String senderEmail;
    private final String recipientEmail;
    private final String dateTimeSent;
    private final String messageContent;
    private final boolean readStatus;

    public SendNewMessageActivityRequest(String senderEmail,
                                         String recipientEmail,
                                         String dateTimeSent,
                                         String messageContent,
                                         boolean readStatus) {
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
        this.dateTimeSent = dateTimeSent;
        this.messageContent = messageContent;
        this.readStatus = readStatus;
    }

    public String getSenderEmail() { return senderEmail; }

   public String getRecipientEmail() { return recipientEmail; }

   public String getDateTimeSent() {
        return dateTimeSent;
    }

   public String getMessageContent() {
        return messageContent;
   }

   public boolean getReadStatus() {
        return readStatus;
    }

    @Override
    public String toString() {
        return "UpdateUserProfileActivityRequest{" +
                "senderEmail='" + senderEmail + '\'' +
                "recipientEmail='" + recipientEmail + '\'' +
                "dateTimeSent='" + dateTimeSent + '\'' +
                "messageContent='" + messageContent + '\'' +
                "readStatus='" + readStatus + '\'' +
                '}';
    }


    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    @JsonPOJOBuilder
    public static class Builder {
        private String senderEmail;
        private String recipientEmail;
        private String dateTimeSent;
        private String messageContent;
        private boolean readStatus;

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

      public Builder withMessageContent(String messageContent) {
                this.messageContent = messageContent;
                return this;
        }

      public Builder withReadStatus(boolean readStatus) {
                this.readStatus = readStatus;
                return this;
        }

     public SendNewMessageActivityRequest build() {
                return new SendNewMessageActivityRequest(senderEmail,
                                                         recipientEmail,
                                                         dateTimeSent,
                                                         messageContent,
                                                         readStatus);
        }
    }
}
