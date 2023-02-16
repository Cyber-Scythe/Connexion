package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetUserInboxActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetUserInboxActivityResult;
import com.nashss.se.connexionservice.dynamodb.MessageDao;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class GetUserInboxActivity {
    private final Logger log = LogManager.getLogger();
    private final MessageDao messageDao;

    /**
     * Instantiates a new GetConnexionsActivity object.
     *
     * @param messageDao MessageDao to access the inbox table.
     */
    @Inject
    public GetUserInboxActivity(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    /**
     * This method handles the incoming request by retrieving the messages (Messages) from the database.
     * <p>
     * It then returns the list of messages (Message).
     * <p>
     * If the message does not exist, this should throw a MessageNotFound.
     *
     * @return GetUserInboxActivityResult result object
     */
    public GetUserInboxActivityResult handleRequest(final GetUserInboxActivityRequest getUserInboxActivityRequest) {
        log.info("Inside GetUserInboxActivityResult handleRequest");

        List<Message> messages = messageDao.getAllMessages(getUserInboxActivityRequest.getCurrUserEmail());

        return GetUserInboxActivityResult.builder()
                .withMessages(messages)
                .build();
    }
}
