package com.nashss.se.connexionservice.converters;

import com.nashss.se.connexionservice.dynamodb.models.Message;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.models.MessageModel;
import com.nashss.se.connexionservice.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class ModelConverter {
    /**
     * Converts a provided {@link User} into a {@link UserModel} representation.
     *
     * @param user the user to convert
     * @return the converted user
     */
     public UserModel toUserModel(User user) {
         List<String> hobbies = null;
         List<User> connexions = null;

         if (user.getHobbies() != null) {
             hobbies = new ArrayList<>(user.getHobbies());
         }

         if(user.getConnexions() != null) {
             connexions = new ArrayList<>(user.getConnexions());
         }

         return UserModel.builder()
                 .withName(user.getName())
                 .withEmail(user.getEmail())
                 .withId(user.getId())
                 .withAge(user.getAge())
                 .withCity(user.getCity())
                 .withState(user.getState())
                 .withPersonalityType(user.getPersonalityType())
                 .withHobbies(hobbies)
                 .withConnexions(connexions)
                 .build();
     }


    /**
     * Converts a list of Users to a list of UserModels.
     *
     * @param users The Users to convert to UserModels
     * @return The converted list of UserModels
     */
    public List<UserModel> toUserModelList(List<User> users) {
        List<UserModel> userModels = new ArrayList<>();

        for (User user : users) {
            userModels.add(toUserModel(user));
        }

        return userModels;
    }


    /**
     * Converts a provided Message into a MessageModel representation.
     *
     * @param message the Message to convert to MessageModel
     * @return the converted MessageModel with fields mapped from message
     */
    public MessageModel toMessageModel(Message message) {
        return MessageModel.builder()
                .withMessageId(message.getMessageId())
                .withDateTimeSent(message.getDateTimeSent())
                .withSentBy(message.getSentBy())
                .withReceivedBy(message.getReceivedBy())
                .withMessageContent(message.getMessageContent())
                .withReadStatus(message.getReadStatus())
                .build();
    }


    /**
     * Converts a list of Messages to a list of MessageModels.
     *
     * @param messages The Messages to convert to MessageModels
     * @return The converted list of MessageModels
     */
    public List<MessageModel> toMessageModelList(List<Message> messages) {
        List<MessageModel> messageModels = new ArrayList<>();

        for (Message msg : messages) {
            messageModels.add(toMessageModel(msg));
        }

        return messageModels;
    }
}
