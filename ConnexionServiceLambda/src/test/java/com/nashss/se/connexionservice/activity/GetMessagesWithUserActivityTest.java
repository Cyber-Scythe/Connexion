package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetMessagesWithUserActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetMessagesWithUserActivityResult;
import com.nashss.se.connexionservice.dynamodb.MessageDao;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import com.nashss.se.connexionservice.dynamodb.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetMessagesWithUserActivityTest {
    @Mock
    private MessageDao messageDao;

    private GetMessagesWithUserActivity getMessagesWithUserActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getMessagesWithUserActivity = new GetMessagesWithUserActivity(messageDao);
    }

    @Test
    public void handleRequest_withUserEmails_returnsAllMessagesBetweenTwoUsers() {
        // GIVEN
        User currUser = new User();
        currUser.setId("1111111111111");
        currUser.setEmail("currUserEmail");

        String otherUserEmail = "otherUserEmail";

        Message msg1 = new Message();
        msg1.setReceivedBy(currUser.getEmail());
        msg1.setSentBy(otherUserEmail);
        msg1.setDateTimeSent("2023-02-23T09:46:54.970761");
        msg1.setMessageContent("content");
        msg1.setReadStatus(false);

        Message msg2 = new Message();
        msg2.setSentBy(currUser.getEmail());
        msg2.setReceivedBy(otherUserEmail);
        msg2.setDateTimeSent(LocalDateTime.now().toString());
        msg2.setMessageContent("content");
        msg2.setReadStatus(true);

        List<Message> messageList = List.of(msg2, msg1);
        ;
        when(messageDao.getMessagesWithUser(currUser.getEmail(), otherUserEmail)).thenReturn(messageList);

        GetMessagesWithUserActivityRequest request = GetMessagesWithUserActivityRequest.builder()
                .withUserId(currUser.getId())
                .withCurrUserEmail(currUser.getEmail())
                .withOtherUserEmail(otherUserEmail)
                .build();

        // WHEN
        GetMessagesWithUserActivityResult result = getMessagesWithUserActivity.handleRequest(request);

        // THEN
        assertEquals(messageList, result.getMessages());
    }
}
