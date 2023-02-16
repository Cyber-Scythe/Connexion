package com.nashss.se.connexionservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

import static com.nashss.se.connexionservice.utils.CollectionUtils.copyToList;

@JsonDeserialize(builder = SendNewMessageActivityRequest.Builder.class)
public class SendNewMessageActivityRequest {
    private final String messageId;
    private final String senderEmail;
    private final String recipientEmail;
    private final String dateTimeSent;
    private final String messageContent;
    private final boolean readStatus;

    public SendNewMessageActivityRequest(String messageId,
                                         String senderEmail,
                                         String recipientEmail,
                                         String dateTimeSent,
                                         String messageContent,
                                         boolean readStatus) {
        this.messageId = messageId;
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
        this.dateTimeSent = dateTimeSent;
        this.messageContent = messageContent;
        this.readStatus = readStatus;
    }

    public String getMessageId() {
            return messageId;
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
                "messageId='" + messageId + '\'' +
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
        private String messageId;
        private String senderEmail;
        private String recipientEmail;
        private String dateTimeSent;
        private String messageContent;
        private boolean readStatus;

        public Builder withMessageId(String messageId) {
                this.messageId = messageId;
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
                return new SendNewMessageActivityRequest(messageId,
                                                         senderEmail,
                                                         recipientEmail,
                                                         dateTimeSent,
                                                         messageContent,
                                                         readStatus);
        }
    }
}
