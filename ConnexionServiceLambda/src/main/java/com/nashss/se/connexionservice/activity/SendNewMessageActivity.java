package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.SendNewMessageActivityRequest;
import com.nashss.se.connexionservice.activity.results.SendNewMessageActivityResult;
import com.nashss.se.connexionservice.converters.ModelConverter;
import com.nashss.se.connexionservice.dynamodb.MessageDao;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import com.nashss.se.connexionservice.metrics.MetricsPublisher;
import com.nashss.se.connexionservice.models.MessageModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class SendNewMessageActivity {
    private final Logger log = LogManager.getLogger();
    private final MessageDao messageDao;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a new SendNewMessageActivity object.
     *
     * @param messageDao MessageDao to access the inbox table.
     * @param metricsPublisher MetricsPublisher to publish metrics.
     */
    @Inject
    public SendNewMessageActivity(MessageDao messageDao, MetricsPublisher metricsPublisher) {
        this.messageDao = messageDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by saving the new message to the inbox table
     * <p>
     * It then returns the message that was sent.
     * <p>
     * @param sendNewMessageActivityRequest request object containing the playlist ID, playlist name, and customer ID
     *                              associated with it
     * @return sendNewMessageActivityResult result object containing the API defined {@link MessageModel}
     */
    public SendNewMessageActivityResult handleRequest(final SendNewMessageActivityRequest sendNewMessageActivityRequest) {
        log.info("Received SendNewMessageActivityRequest {}", sendNewMessageActivityRequest);

        Message newMessage = new Message();

        newMessage.setSentBy(sendNewMessageActivityRequest.getSenderEmail());
        newMessage.setReceivedBy(sendNewMessageActivityRequest.getRecipientEmail());
        newMessage.setDateTimeSent(sendNewMessageActivityRequest.getDateTimeSent());
        newMessage.setMessageContent(sendNewMessageActivityRequest.getMessageContent());
        newMessage.setReadStatus(sendNewMessageActivityRequest.getReadStatus());

        messageDao.sendMessage(newMessage);

        return SendNewMessageActivityResult.builder()
                .withMessage(new ModelConverter().toMessageModel(newMessage))
                .build();
    }

}
