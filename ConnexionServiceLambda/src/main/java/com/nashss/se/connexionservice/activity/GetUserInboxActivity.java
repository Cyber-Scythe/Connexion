package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetUserInboxActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetUserInboxActivityResult;
import com.nashss.se.connexionservice.dynamodb.MessageDao;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import com.nashss.se.connexionservice.utils.DateTimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetUserInboxActivity {
    private final Logger log = LogManager.getLogger();
    private final MessageDao messageDao;

    private final DateTimeUtils dateTimeUtils;

    /**
     * Instantiates a new GetConnexionsActivity object.
     *
     * @param messageDao MessageDao to access the inbox table.
     */
    @Inject
    public GetUserInboxActivity(MessageDao messageDao, DateTimeUtils dateTimeUtils) {

        this.messageDao = messageDao;
        this.dateTimeUtils = dateTimeUtils;
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
        List<String> conversationUsers = new ArrayList<>();

        List<Message> mostRecent = new ArrayList<>();


        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getSentBy() != getUserInboxActivityRequest.getCurrUserEmail()) {
                conversationUsers.add(messages.get(i).getSentBy());

            } else if (messages.get(i).getReceivedBy() != getUserInboxActivityRequest.getCurrUserEmail()) {
                conversationUsers.add(messages.get(i).getReceivedBy());
            }
        }


        for (String user : conversationUsers) {
            List<Message> allMsg = new ArrayList<>();

            for (Message msg : messages) {
                if (msg.getReceivedBy().equals(user) || msg.getSentBy().equals(user)) {
                    allMsg.add(msg);
                }
            }

            Message recent = allMsg.get(0);
            Date recentDate = dateTimeUtils.convertStringToDateTime(recent.getDateTimeSent());
            for (int i = 1; i < allMsg.size(); i++) {
                Date dateTime =  dateTimeUtils.convertStringToDateTime(allMsg.get(i).getDateTimeSent());

                if (dateTime.after(recentDate)) {
                    recent = allMsg.get(i);
                    recentDate = dateTimeUtils.convertStringToDateTime((allMsg.get(i).getDateTimeSent()));
                }
            }

            mostRecent.add(recent);
        }

        return GetUserInboxActivityResult.builder()
                .withMessages(mostRecent)
                .build();
    }



}
