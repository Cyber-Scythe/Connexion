package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetMessagesWithUserActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetMessagesWithUserActivityResult;
import com.nashss.se.connexionservice.dynamodb.MessageDao;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import com.nashss.se.connexionservice.utils.LocalDateTimeComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.*;

public class GetMessagesWithUserActivity {
    private final Logger log = LogManager.getLogger();
    private final MessageDao messageDao;

    /**
     * Instantiates a new GetMessagesWithUserActivity object.
     *
     * @param messageDao MessageDao to access the inbox table.
     */
    @Inject
    public GetMessagesWithUserActivity(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    /**
     * This method handles the incoming request by retrieving the messages between two users from the database.
     * <p>
     * It then returns the list of messages (Message).
     * <p>
     * @return GetMessagesFromUserActivityResult result object
     */
    public GetMessagesWithUserActivityResult handleRequest(final GetMessagesWithUserActivityRequest
                                                                   getMessagesWithUserActivityRequest) {
        log.info("Inside GetMessagesFromWithActivityResult handleRequest");

        List<Message> messages = messageDao.getMessagesWithUser(getMessagesWithUserActivityRequest.getCurrUserEmail(),
                getMessagesWithUserActivityRequest.getOtherUserEmail());
        List<Message> msgArraylist = new ArrayList<>(messages);
        List<Message> sortedMsg = sortedMessages(msgArraylist);

        return GetMessagesWithUserActivityResult.builder()
                .withMessages(sortedMsg)
                .build();
    }

    public List<Message> sortedMessages(List<Message> messages) {
        Collections.sort(messages, new LocalDateTimeComparator());
        return messages;
    }
}
