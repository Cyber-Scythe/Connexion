package com.nashss.se.connexionservice.activity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.connexionservice.activity.requests.DeleteMessagesActivityRequest;
import com.nashss.se.connexionservice.activity.results.DeleteMessagesActivityResult;
import com.nashss.se.connexionservice.dynamodb.MessageDao;
import com.nashss.se.connexionservice.dynamodb.models.Message;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteMessageActivityTest {
    @InjectMocks
    private MessageDao messageDao;

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    private DeleteMessagesActivity deleteMessagesActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        deleteMessagesActivity = new DeleteMessagesActivity(messageDao);
    }

    @Test
    public void handleRequest_messageFound_deletesMessageFromDb() {
        // GIVEN
        String expectedSenderEmail = "expectedSenderEmail";
        String expectedDateTimeSent = "expectedDateTimeSent";

        Message msg = new Message();
        msg.setDateTimeSent(expectedDateTimeSent);
        msg.setSentBy(expectedSenderEmail);

        DeleteMessagesActivityRequest request = DeleteMessagesActivityRequest.builder()
                .withDateTimeSent(expectedDateTimeSent)
                .withSenderEmail(expectedSenderEmail)
                .build();

        // WHEN
        boolean daoResult = messageDao.deleteMessages(msg);
        DeleteMessagesActivityResult result = deleteMessagesActivity.handleRequest(request);

        // THEN
        assertEquals(daoResult, result.getResult());
        verify(dynamoDBMapper, never()).load(any(), any(), any());
    }
}