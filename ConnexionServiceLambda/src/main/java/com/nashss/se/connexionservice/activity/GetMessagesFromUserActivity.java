package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetMessagesFromUserActivityRequest;
import com.nashss.se.connexionservice.activity.requests.GetUserInboxActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetMessagesFromUserActivityResult;
import com.nashss.se.connexionservice.activity.results.GetUserInboxActivityResult;
import com.nashss.se.connexionservice.dynamodb.MessageDao;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class GetMessagesFromUserActivity {
    private final Logger log = LogManager.getLogger();
    private final MessageDao messageDao;

    /**
     * Instantiates a new GetConnexionsActivity object.
     *
     * @param messageDao MessageDao to access the inbox table.
     */
    @Inject
    public GetMessagesFromUserActivity(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    /**
     * This method handles the incoming request by retrieving the messages (Messages) from the database.
     * <p>
     * It then returns the list of messages (Message).
     * <p>
     * If the message does not exist, this should throw a MessageNotFound.
     *
     * @return GetMessagesFromUserActivityResult result object
     */
    public GetMessagesFromUserActivityResult handleRequest(final GetMessagesFromUserActivityRequest
                                                                   getMessagesFromUserActivityRequest) {
        log.info("Inside GetMessagesFromUserActivityResult handleRequest");

        List<Message> messages = messageDao.getMessagesWithUser(getMessagesFromUserActivityRequest.getCurrUserEmail(),
                                                                getMessagesFromUserActivityRequest.getOtherUserEmail());

        return GetMessagesFromUserActivityResult.builder()
                .withMessages(messages)
                .build();
    }
}
