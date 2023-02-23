package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetUserInboxActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetUserInboxActivityResult;
import com.nashss.se.connexionservice.dynamodb.MessageDao;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import com.nashss.se.connexionservice.dynamodb.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetUserInboxActivityTest {
    @Mock
    private MessageDao messageDao;

    private GetUserInboxActivity getUserInboxActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getUserInboxActivity = new GetUserInboxActivity(messageDao);
    }

    @Test
    public void handleRequest_withUserEmail_returnsListOfUsersMostRecentMessages() {
        // GIVEN
        User currUser = new User();
        currUser.setId("1111111111");
        currUser.setEmail("currUserEmail");

        String recipientEmail = "recipientEmail";

        Message msg1 = new Message();
        msg1.setReceivedBy(currUser.getEmail());
        msg1.setSentBy(recipientEmail);
        msg1.setDateTimeSent("2023-02-23T10:46:54.970761");
        msg1.setMessageContent("content");
        msg1.setReadStatus(false);

        Message msg2 = new Message();
        msg2.setSentBy(currUser.getEmail());
        msg2.setReceivedBy(recipientEmail);
        msg2.setDateTimeSent("2023-02-23T09:46:54.970761");
        msg2.setMessageContent("content");
        msg2.setReadStatus(true);

        List<Message> inboxList = List.of(msg1, msg2);
        List<Message> mostRecentList = List.of(msg1);

        when(messageDao.getAllMessages(currUser.getEmail())).thenReturn(inboxList);

        GetUserInboxActivityRequest request = GetUserInboxActivityRequest.builder()
                .withUserId(currUser.getId())
                .withCurrUserEmail(currUser.getEmail())
                .build();

        // WHEN
        GetUserInboxActivityResult result = getUserInboxActivity.handleRequest(request);

        // THEN
        assertEquals(mostRecentList, result.getMessages());
    }
}
