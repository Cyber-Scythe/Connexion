package com.nashss.se.connexionservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;


/**
 * Represents a record in the inbox table.
 */
@DynamoDBTable(tableName = "inbox")
public class Message {
    private String dateTimeSent;
    private String sentBy;
    private String receivedBy;
    private String messageContent;
    private boolean readStatus;

    @DynamoDBRangeKey(attributeName = "dateTimeSent")
    public String getDateTimeSent() {

        return dateTimeSent;
    }

    public void setDateTimeSent(String dateTimeSent) {

        this.dateTimeSent = dateTimeSent;
    }

    @DynamoDBHashKey(attributeName = "sentBy")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "EmailsSentAndReceivedIndex",
            attributeName = "sentBy")
    public String getSentBy() {

        return sentBy;
    }

    public void setSentBy(String sentBy) {

        this.sentBy = sentBy;
    }

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "EmailsSentAndReceivedIndex",
            attributeName = "receivedBy")
    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    @DynamoDBAttribute(attributeName = "messageContent")
    public String getMessageContent() {

        return messageContent;
    }

    public void setMessageContent(String messageContent) {

        this.messageContent = messageContent;
    }

    @DynamoDBAttribute(attributeName = "readStatus")
    public boolean getReadStatus() {

        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {

        this.readStatus = readStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Message message = (Message) o;
        return dateTimeSent.equals(message.dateTimeSent) &&
                sentBy.equals(message.sentBy) &&
                receivedBy.equals(message.receivedBy) &&
                messageContent.equals(message.messageContent) &&
                readStatus == message.readStatus;
    }

    @Override
    public int hashCode() {

        return Objects.hash(dateTimeSent, sentBy, receivedBy, messageContent, readStatus);
    }
}
