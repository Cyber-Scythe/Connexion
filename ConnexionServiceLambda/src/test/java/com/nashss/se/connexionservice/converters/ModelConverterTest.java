package com.nashss.se.connexionservice.converters;

import com.nashss.se.connexionservice.dynamodb.models.User;
import com.nashss.se.connexionservice.models.UserModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.nashss.se.connexionservice.utils.CollectionUtils.copyToList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ModelConverterTest {
    private ModelConverter modelConverter = new ModelConverter();

    @Test
    void toUserModel_withTags_convertsUser() {
        User user = new User();
        user.setId("id");
        user.setName("name");
        user.setEmail("email");
        user.setBirthdate("birthdate");
        user.setCity("city");
        user.setState("state");
        user.setPersonalityType("personalityType");
        user.setHobbies(List.of("hobbies"));
        user.setConnections(List.of("connections"));

        UserModel userModel = modelConverter.toUserModel(user);

        assertEquals(user.getId(), userModel.getUserId());
        assertEquals(user.getName(), userModel.getUserName());
        assertEquals(user.getEmail(), userModel.getUserEmail());
        assertEquals(user.getBirthdate(), userModel.getUserBirthdate());
        assertEquals(user.getCity(), userModel.getUserCity());
        assertEquals(user.getState(), userModel.getUserState());
        assertEquals(user.getPersonalityType(), userModel.getUserPersonalityType());
        assertEquals(user.getHobbies(), copyToList(userModel.getUserHobbies()));
        assertEquals(user.getConnections(), copyToList(userModel.getUserConnections()));
    }

    @Test
    void toUserModel_nullConnections_convertsUser() {
        User user = new User();
        user.setId("id");
        user.setName("name");
        user.setEmail("email");
        user.setBirthdate("birthdate");
        user.setCity("city");
        user.setState("state");
        user.setPersonalityType("personalityType");
        user.setHobbies(List.of("hobbies"));
        user.setConnections(List.of(null));

        UserModel userModel = modelConverter.toUserModel(user);

        assertEquals(user.getId(), userModel.getUserId());
        assertEquals(user.getName(), userModel.getUserName());
        assertEquals(user.getEmail(), userModel.getUserEmail());
        assertEquals(user.getBirthdate(), userModel.getUserBirthdate());
        assertEquals(user.getCity(), userModel.getUserCity());
        assertEquals(user.getState(), userModel.getUserState());
        assertEquals(user.getPersonalityType(), userModel.getUserPersonalityType());
        assertEquals(user.getHobbies(), copyToList(userModel.getUserHobbies()));
        assertNull(userModel.getUserConnections());
    }
}

