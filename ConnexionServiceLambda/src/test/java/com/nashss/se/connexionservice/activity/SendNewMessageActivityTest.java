package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.SendNewMessageActivityRequest;
import com.nashss.se.connexionservice.activity.results.SendNewMessageActivityResult;
import com.nashss.se.connexionservice.dynamodb.MessageDao;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SendNewMessageActivityTest {
    @Mock
    private MessageDao messageDao;

    private MetricsPublisher metricsPublisher;

    private SendNewMessageActivity sendNewMessageActivity;


    @BeforeEach
    public void setUp() {
        initMocks(this);
        sendNewMessageActivity = new SendNewMessageActivity(messageDao, metricsPublisher);
    }

    @Test
    public void handleRequest_withMessage_savesNewMessageToDb() {
        // GIVEN
        Message newMessage = new Message();

        newMessage.setSentBy("senderEmail");
        newMessage.setReceivedBy("RecipientEmail");
        newMessage.setDateTimeSent(LocalDateTime.now().toString());
        newMessage.setMessageContent("content");
        newMessage.setReadStatus(false);

        when(messageDao.sendMessage(newMessage)).thenReturn(newMessage);

        SendNewMessageActivityRequest request = SendNewMessageActivityRequest.builder()
                .withSenderEmail(newMessage.getSentBy())
                .withRecipientEmail(newMessage.getReceivedBy())
                .withDateTimeSent(newMessage.getDateTimeSent())
                .withMessageContent(newMessage.getMessageContent())
                .withReadStatus(newMessage.getReadStatus())
                .build();

        // WHEN
        SendNewMessageActivityResult result = sendNewMessageActivity.handleRequest(request);

        // THEN
        assertEquals(newMessage.getSentBy(), result.getMessage().getSender());
        assertEquals(newMessage.getReceivedBy(), result.getMessage().getRecipient());
        assertEquals(newMessage.getDateTimeSent(), result.getMessage().getDateTimeSent());
        assertEquals(newMessage.getMessageContent(), result.getMessage().getMessageContent());
        assertEquals(newMessage.getReadStatus(), result.getMessage().getReadStatus());
    }
}
