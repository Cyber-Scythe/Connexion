package com.nashss.se.connexionservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class MessageDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @Mock
    private PaginatedScanList<Message> scanResult;

    @InjectMocks
    private MessageDao messageDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        messageDao = new MessageDao(dynamoDBMapper);
    }

    @Test
    public void sendMessage_callsMapperWithMessage() {
        // GIVEN
        Message message = new Message();

        // WHEN
        Message result = messageDao.sendMessage(message);

        // THEN
        verify(dynamoDBMapper).save(message);
        assertEquals(message, result);
    }

    @Test
    public void getAllMessages_callsScanWithScanExpression_returnsAllMessages() {
        // GIVEN
        String currUserEmail = "currUserEmail";

        when(dynamoDBMapper.scan(eq(Message.class), any())).thenReturn(scanResult);

        ArgumentCaptor<DynamoDBScanExpression> scanExpressionArgumentCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        // WHEN
        List<Message> scanList = messageDao.getAllMessages(currUserEmail);

        // THEN
        verify(dynamoDBMapper).scan(eq(Message.class), scanExpressionArgumentCaptor.capture());
        DynamoDBScanExpression scanExpression = scanExpressionArgumentCaptor.getValue();
        Map<String, AttributeValue> valueMap = scanExpression.getExpressionAttributeValues();

        assertNotNull(valueMap, "Expected the expression attribute value map to be set in the scan expression");
        assertEquals(valueMap.size(), 1, "Expected currUserEmail to be set in " +
                "attribute value map");

        assertNotNull(scanExpression.getFilterExpression(), "Expected the scan expression to contain a filter" +
                " expression");

        assertEquals(scanResult, scanList, "Expected method to return the results of the scan");
    }

    @Test
    public void getMessagesWithUser_callsScanWithScanExpression_returnsAllMessagesBetweenTwoUsers() {
        // GIVEN
        String senderEmail = "senderEmail";
        String recipientEmail = "recipientEmail";

        when(dynamoDBMapper.scan(eq(Message.class), any())).thenReturn(scanResult);

        ArgumentCaptor<DynamoDBScanExpression> scanExpressionArgumentCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        // WHEN
        List<Message> scanList = messageDao.getMessagesWithUser(senderEmail, recipientEmail);

        // THEN
        verify(dynamoDBMapper).scan(eq(Message.class), scanExpressionArgumentCaptor.capture());
        DynamoDBScanExpression scanExpression = scanExpressionArgumentCaptor.getValue();
        Map<String, AttributeValue> valueMap = scanExpression.getExpressionAttributeValues();

        assertNotNull(valueMap, "Expected the expression attribute value map to be set in the scan expression");
        assertEquals(valueMap.size(), 2, "Expected senderEmail and recipientEmail to be set in " +
                "attribute value map");


        assertNotNull(scanExpression.getFilterExpression(), "Expected the scan expression to contain a filter" +
                " expression");

        assertEquals(scanResult, scanList, "Expected method to return the results of the scan");
    }

    @Test
    public void deleteMessages_deletesMessageFromDb_returnsTrue() {
        // GIVEN
        Message msg = new Message();
        msg.setSentBy("sentBy");
        msg.setReceivedBy("receivedBy");

        // WHEN
        boolean result = messageDao.deleteMessages(msg);

       // THEN
       assertTrue(result);
       verify(dynamoDBMapper, never()).load(any(), any(), any());

    }
}
