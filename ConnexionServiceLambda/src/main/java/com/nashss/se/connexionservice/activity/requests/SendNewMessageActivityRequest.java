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

    /**
     * Constructor for SendNewMessageActivityRequest.
     * @param senderEmail The sender's email
     * @param recipientEmail Recipient's email
     * @param dateTimeSent The date and time sent
     * @param messageContent The message content
     * @param readStatus Whether the message has been read or not.
     */
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

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

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
    public static Builder builder() {

        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String sender;
        private String recipient;
        private String dateTime;
        private String content;
        private boolean read;

        public Builder withSenderEmail(String senderEmail) {
                this.sender = senderEmail;
                return this;
        }

        public Builder withRecipientEmail(String recipientEmail) {
                this.recipient = recipientEmail;
                return this;
        }

       public Builder withDateTimeSent(String dateTimeSent) {
                this.dateTime = dateTimeSent;
                return this;
        }

      public Builder withMessageContent(String messageContent) {
                this.content = messageContent;
                return this;
        }

      public Builder withReadStatus(boolean readStatus) {
                this.read = readStatus;
                return this;
        }

     public SendNewMessageActivityRequest build() {
                return new SendNewMessageActivityRequest(sender,
                                                         recipient,
                                                         dateTime,
                                                         content,
                                                         read);
        }
    }
}
