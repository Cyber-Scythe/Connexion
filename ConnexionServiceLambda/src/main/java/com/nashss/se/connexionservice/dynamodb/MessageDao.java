package com.nashss.se.connexionservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessageDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final Logger log = LogManager.getLogger();

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
     * Saves new message to inbox table.
     * @return message that was saved
     */
    public Message sendMessage(Message message) {
        this.dynamoDbMapper.save(message);

        return message;
    }

    /**
     * Retrieves all messages between two users in inbox table.
     * <p>
     * If not found, throws MessageNotFoundException.
     *
     * @return All messages between two users in inbox table
     */
    public List<Message> getAllMessages(String currUserEmail) {
        Map<String, AttributeValue> valueMap = new HashMap<>();

        valueMap.put(":currUserEmail", new AttributeValue().withS(currUserEmail));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withIndexName("EmailsSentAndReceivedIndex")
                .withFilterExpression("sentBy = :currUserEmail OR receivedBy = :currUserEmail ")
                .withExpressionAttributeValues(valueMap);

        List<Message> scanResult = dynamoDbMapper.scan(Message.class, scanExpression);

        return scanResult;
    }

    /**
     * Retrieves all messages between two users in inbox table.
     * <p>
     * If not found, throws MessageNotFoundException.
     *
     * @return All messages between two users in inbox table
     */
    public List<Message> getMessagesWithUser(String senderEmail, String recipientEmail) {
        System.out.println("senderEmail: " + senderEmail + "   recipientEmail: " + recipientEmail);

        Map<String, AttributeValue> valueMap = new HashMap<>();

        valueMap.put(":currUserEmail", new AttributeValue().withS(senderEmail));
        valueMap.put(":otherUserEmail", new AttributeValue().withS(recipientEmail));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("sentBy = :currUserEmail AND receivedBy = :otherUserEmail " +
                        "OR receivedBy = :currUserEmail AND sentBy = :otherUserEmail")
                .withExpressionAttributeValues(valueMap);


        return dynamoDbMapper.scan(Message.class, scanExpression);
    }

    public Message deleteMessages(Message message) {

        dynamoDbMapper.delete(message);
        return message;
    }
}
