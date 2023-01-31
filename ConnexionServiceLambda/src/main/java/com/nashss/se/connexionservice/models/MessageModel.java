package com.nashss.se.connexionservice.models;

import java.util.Objects;

public class MessageModel {
    private final String messageId;
    private final String dateTimeSent;
    private final String sentBy;
    private final String receivedBy;
    private final String messageContent;
    private final boolean readStatus;


    private MessageModel(String messageId,
                          String dateTimeSent,
                          String sentBy,
                          String receivedBy,
                          String messageContent,
                          boolean readStatus) {

        this.messageId = messageId;
        this.dateTimeSent = dateTimeSent;
        this.sentBy = sentBy;
        this.receivedBy = receivedBy;
        this.messageContent = messageContent;
        this.readStatus = readStatus;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getDateTimeSent() {
        return dateTimeSent;
    }

    public String getSentBy() { return sentBy; }
    public String getReceivedBy() {
        return receivedBy;
    }

    public String getMessageContent() {
        return messageContent;
    }
    public boolean getReadStatus() { return readStatus; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageModel that = (MessageModel) o;

        return Objects.equals(messageId, that.messageId) &&
                Objects.equals(dateTimeSent, that.dateTimeSent) &&
                Objects.equals(sentBy, that.sentBy) &&
                Objects.equals(receivedBy, that.receivedBy) &&
                Objects.equals(messageContent, that.messageContent) &&
                Objects.equals(readStatus, that.readStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, dateTimeSent, sentBy, receivedBy, messageContent, readStatus);
    }

    //CHECKSTYLE:OFF:Builder
    public static MessageModel.Builder builder() {
        return new MessageModel.Builder();
    }

    public String getId() {
        return messageId;
    }
    public String getDateTime() { return dateTimeSent; }
    public String getSender() { return sentBy; }
    public String getRecipient() { return receivedBy ;}
    public String getContent() { return messageContent; }
    public boolean getStatus() { return readStatus; }


    public static class Builder {
        private String messageId;
        private String dateTimeSent;
        private String sentBy;
        private String receivedBy;
        private String messageContent;
        private boolean readStatus;

        public MessageModel.Builder withMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public MessageModel.Builder withDateTimeSent(String dateTimeSent) {
            this.dateTimeSent = dateTimeSent;
            return this;
        }

        public MessageModel.Builder withSentBy(String sentBy) {
            this.sentBy = sentBy;
            return this;
        }

        public MessageModel.Builder withReceivedBy(String receivedBy) {
            this.receivedBy = receivedBy;
            return this;
        }

        public MessageModel.Builder withMessageContent(String messageContent) {
            this.messageContent = messageContent;
            return this;
        }

        public MessageModel.Builder withReadStatus(boolean readStatus) {
            this.readStatus = readStatus;
            return this;
        }

        public MessageModel build() {
            return new MessageModel(messageId,
                                    dateTimeSent,
                                    sentBy,
                                    receivedBy,
                                    messageContent,
                                    readStatus);
        }
    }
}
