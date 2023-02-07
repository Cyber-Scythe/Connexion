package com.nashss.se.connexionservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import com.nashss.se.connexionservice.models.MessageModel;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Accesses data for an message using {@link MessageModel} to represent the model in DynamoDB.
 */
@Singleton
public class MessageDao {
    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates an MessageDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the inbox table
     */
    @Inject
    public MessageDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Retrieves an AlbumTrack by ASIN and track number.
     *
     * If not found, throws AlbumTrackNotFoundException.
     *
     * @param sentBy The sender email address to look up
     * @param receivedBy The recipient email address to look up
     * @return The corresponding Message if found
     */
    public Message getMessage(String sentBy, String receivedBy) {
        Message message = dynamoDbMapper.load(Message.class, sentBy, receivedBy);
        if (null == message) {
            //throw new MessageNotFoundException(
            //      String.format("Could not find message with sender email %d and recipient email %d", sentBy, receivedBy));
        }

        return message;
    }
}
