package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.DeleteMessagesActivityRequest;
import com.nashss.se.connexionservice.activity.results.DeleteMessagesActivityResult;
import com.nashss.se.connexionservice.dynamodb.MessageDao;
import com.nashss.se.connexionservice.dynamodb.models.Message;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteMessageActivityTest {
    @Mock
    private MessageDao messageDao;

    private DeleteMessagesActivity deleteMessagesActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        deleteMessagesActivity = new DeleteMessagesActivity(messageDao);
    }

    @Test
    @Disabled
    public void handleRequest_messageFound_deletesMessageFromDb() {
        // GIVEN
        String expectedSenderEmail = "expectedSenderEmail";
        String expectedDateTimeSent = "expectedDateTimeSent";

        Message msg = new Message();
        msg.setDateTimeSent(expectedDateTimeSent);
        msg.setSentBy(expectedSenderEmail);

        when(messageDao.deleteMessages(msg)).thenReturn(msg);

        DeleteMessagesActivityRequest request = DeleteMessagesActivityRequest.builder()
                .withDateTimeSent(expectedDateTimeSent)
                .withSenderEmail(expectedSenderEmail)
                .build();

        // WHEN
        DeleteMessagesActivityResult result = deleteMessagesActivity.handleRequest(request);

        // THEN
        assertEquals(expectedSenderEmail, result.getMessage().getSender());
        assertEquals(expectedDateTimeSent, result.getMessage().getDateTimeSent());
    }
}