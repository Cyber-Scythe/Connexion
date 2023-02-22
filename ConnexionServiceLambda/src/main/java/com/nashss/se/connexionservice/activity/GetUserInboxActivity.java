package com.nashss.se.connexionservice.activity;

import com.nashss.se.connexionservice.activity.requests.GetUserInboxActivityRequest;
import com.nashss.se.connexionservice.activity.results.GetUserInboxActivityResult;
import com.nashss.se.connexionservice.dynamodb.MessageDao;
import com.nashss.se.connexionservice.dynamodb.models.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDateTime;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GetUserInboxActivity {
    private final Logger log = LogManager.getLogger();
    private final MessageDao messageDao;

    /**
     * Instantiates a new GetUserInboxActivity object.
     *
     * @param messageDao MessageDao to access the inbox table.
     */
    @Inject
    public GetUserInboxActivity(MessageDao messageDao) {

        this.messageDao = messageDao;
    }

    /**
     * This method handles the incoming request by retrieving the messages from the database.
     * <p>
     * It then returns the list of most recent messages for each conversation.
     * <p>
     * @return GetUserInboxActivityResult result object
     */
    public GetUserInboxActivityResult handleRequest(final GetUserInboxActivityRequest getUserInboxActivityRequest) {
        log.info("Inside GetUserInboxActivityResult handleRequest");

        List<Message> messages = messageDao.getAllMessages(getUserInboxActivityRequest.getCurrUserEmail());
        List<String> conversationUsers = new ArrayList<>();

        List<Message> mostRecent = new ArrayList<>();


        for (Message message : messages) {
            if (message.getSentBy().equals(getUserInboxActivityRequest.getCurrUserEmail())) {
                if (!conversationUsers.contains(message.getReceivedBy())) {
                    conversationUsers.add(message.getReceivedBy());
                }
            } else if (message.getReceivedBy().equals(getUserInboxActivityRequest.getCurrUserEmail())) {
                if (!conversationUsers.contains(message.getSentBy())) {
                    conversationUsers.add(message.getSentBy());
                }
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
            System.out.println("Date: " + recent.getDateTimeSent());
            LocalDateTime now = LocalDateTime.now();
            System.out.println("DateTime Now: " + now);

            LocalDateTime recentDate = LocalDateTime.parse(recent.getDateTimeSent());

            for (int i = 1; i < allMsg.size(); i++) {
                LocalDateTime dateTime = LocalDateTime.parse(allMsg.get(i).getDateTimeSent());

                if (dateTime.isAfter(recentDate)) {
                    recent = allMsg.get(i);
                    recentDate = LocalDateTime.parse(allMsg.get(i).getDateTimeSent());
                }
            }

            mostRecent.add(recent);
        }

        return GetUserInboxActivityResult.builder()
                .withMessages(mostRecent)
                .build();
    }

    public LocalDateTime convertStringToDateTime(String dateTimeSent) {
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeSent);

        System.out.println(dateTime);
        return dateTime;
    }

}
