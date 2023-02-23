package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.DeleteMessagesActivityRequest;
import com.nashss.se.connexionservice.activity.results.DeleteMessagesActivityResult;
import com.nashss.se.connexionservice.dynamodb.MessageDao;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import com.nashss.se.connexionservice.models.MessageModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the DeleteMessagesActivity for the Connexion's DeleteMessages API.
 * <p>
 * This API deletes the specified message from the database
 */
public class DeleteMessagesActivity {
    private final Logger log = LogManager.getLogger();
    private final MessageDao messageDao;

    /**
     * Instantiates a new DeleteMessagesActivity object.
     *
     * @param messageDao MessageDao to access the inbox table.
     */
    @Inject
    public DeleteMessagesActivity(MessageDao messageDao) {

        this.messageDao = messageDao;
    }

    /**
     * This method handles the incoming request by finding the message in the inbox table.
     * <p>
     * It then returns the deleted message
     * <p>
     * @param deleteMessagesActivityRequest request object containing the sender's email and the date
     *                                      and time the message was sent
     * @return DeleteMessagesActivityResult result object containing the API defined {@link MessageModel}
     */
    public DeleteMessagesActivityResult handleRequest(final DeleteMessagesActivityRequest
                                                              deleteMessagesActivityRequest) {

        log.info("Received DeleteMessagesActivityRequest {}", deleteMessagesActivityRequest);

        Message deleteMessage = new Message();
        deleteMessage.setSentBy(deleteMessagesActivityRequest.getSenderEmail());
        deleteMessage.setDateTimeSent(deleteMessagesActivityRequest.getDateTimeSent());

        boolean result = messageDao.deleteMessages(deleteMessage);

        return DeleteMessagesActivityResult.builder()
                .withResult(result)
                .build();
    }
}
