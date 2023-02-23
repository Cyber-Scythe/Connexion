package com.nashss.se.connexionservice.converters;

import com.nashss.se.connexionservice.dynamodb.models.Message;
import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.models.MessageModel;
import com.nashss.se.connexionservice.models.UserModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.nashss.se.connexionservice.utils.CollectionUtils.copyToList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ModelConverterTest {
    private ModelConverter modelConverter = new ModelConverter();

    @Test
    void toUserModel_withHobbies_convertsUser() {
        User user = new User();
        user.setId("id");
        user.setEmail("email");
        user.setName("name");
        user.setAge(1);
        user.setPersonalityType("TYPE");
        user.setCity("city");
        user.setState("state");
        user.setHobbies(List.of("hobby1", "hobby2", "hobby3"));
        user.setConnexions(List.of("c1", "c2", "c3"));

        UserModel userModel = modelConverter.toUserModel(user);
        assertEquals(user.getId(), userModel.getId());
        assertEquals(user.getName(), userModel.getName());
        assertEquals(user.getAge(), userModel.getAge());
        assertEquals(user.getHobbies(), copyToList(userModel.getHobbies()));
        assertEquals(user.getConnexions(), copyToList(userModel.getConnexions()));
    }

    @Test
    void toUserModel_nullConnexions_convertsUser() {
        User user = new User();
        user.setId("id");
        user.setEmail("email");
        user.setName("name");
        user.setAge(1);
        user.setPersonalityType("TYPE");
        user.setCity("city");
        user.setState("state");
        user.setHobbies(List.of("hobby1", "hobby2", "hobby3"));
        user.setConnexions(null);

        UserModel userModel = modelConverter.toUserModel(user);
        assertEquals(user.getId(), userModel.getId());
        assertEquals(user.getName(), userModel.getName());
        assertEquals(user.getAge(), userModel.getAge());
        assertEquals(user.getHobbies(), copyToList(userModel.getHobbies()));
        assertEquals(user.getConnexions(), copyToList(userModel.getConnexions()));
        assertNull(userModel.getConnexions());
    }

    @Test
    public void toUserModel_nullHobbies_convertsUser() {
        User user = new User();
        user.setId("id");
        user.setEmail("email");
        user.setName("name");
        user.setAge(1);
        user.setPersonalityType("TYPE");
        user.setCity("city");
        user.setState("state");
        user.setHobbies(null);
        user.setConnexions(null);

        UserModel userModel = modelConverter.toUserModel(user);
        assertEquals(user.getId(), userModel.getId());
        assertEquals(user.getName(), userModel.getName());
        assertEquals(user.getAge(), userModel.getAge());
        assertEquals(user.getHobbies(), copyToList(userModel.getHobbies()));
        assertEquals(user.getConnexions(), copyToList(userModel.getConnexions()));
        assertNull(userModel.getConnexions());
    }

    @Test
    void toMessageModel_withMessage_convertsToMessageModel() {
        // GIVEN
        Message message = new Message();
        message.setSentBy("sender");
        message.setReceivedBy("receiver");
        message.setDateTimeSent("dateTime");
        message.setMessageContent("content");
        message.setReadStatus(false);

        // WHEN
        MessageModel result = modelConverter.toMessageModel(message);

        // THEN
        assertEquals(message.getSentBy(), result.getSender());
        assertEquals(message.getReceivedBy(), result.getRecipient());
        assertEquals(message.getDateTimeSent(), result.getDateTime());
        assertEquals(message.getMessageContent(), result.getContent());
        assertEquals(message.getReadStatus(), result.getReadStatus());
    }
}
