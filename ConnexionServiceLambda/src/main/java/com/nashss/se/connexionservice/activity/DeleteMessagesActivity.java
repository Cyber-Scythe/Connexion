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
 * Implementation of the DeleteMessagesActivity for the Connexion's CreatePlaylist API.
 * <p>
 * This API checks the database to see if the user already exists. If they do not then
 * the API to create a new user is triggered.class
 */
public class DeleteMessagesActivity {
    private final Logger log = LogManager.getLogger();
    private final MessageDao messageDao;

    /**
     * Instantiates a new CreateUserActivity object.
     *
     * @param messageDao UserDao to access the users table.
     */
    @Inject
    public DeleteMessagesActivity(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    /**
     * This method handles the incoming request by persisting a new user
     * with the provided user's name, email, city, state, personalityType, and hobbies from the request.
     * <p>
     * It then returns the newly created user.
     * <p>
     * If the provided name, email, city, state, personalityType, or hobbies has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param deleteMessagesActivityRequest request object containing the user's name, email, city, state, personality type,
     *                                  and hobbies associated with it
     * @return DeleteMessagesActivityResult result object containing the API defined {@link MessageModel}
     */
    public DeleteMessagesActivityResult handleRequest(final DeleteMessagesActivityRequest deleteMessagesActivityRequest) {

        log.info("Received DeleteMessagesActivityRequest {}", deleteMessagesActivityRequest);

        Message deleteMessage = new Message();
        deleteMessage.setSentBy(deleteMessagesActivityRequest.getSenderEmail());
        deleteMessage.setReceivedBy(deleteMessagesActivityRequest.getRecipientEmail());
        deleteMessage.setDateTimeSent(deleteMessagesActivityRequest.getDateTimeSent());

        messageDao.deleteMessages(deleteMessage);

        return DeleteMessagesActivityResult.builder()
                .build();
    }
}
