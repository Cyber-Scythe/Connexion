package com.nashss.se.connexionservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "hobbies")
public class Hobby {
    private String hobby;

    public Hobby() {

    }

    public Hobby(String hobby) {

        this.hobby = hobby;
    }

    @DynamoDBHashKey(attributeName = "hobbyName")
    public String getHobby() {

        return hobby;
    }

    public void setHobby(String hobby) {

        this.hobby = hobby;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hobby)) {
            return false;
        }
        Hobby hobby1 = (Hobby) o;
        return Objects.equals(hobby, hobby1.hobby);
    }

    @Override
    public int hashCode() {

        return Objects.hash(hobby);
    }

    @Override
    public String toString() {
        return "Hobby{" +
                "hobbyList=" + hobby +
                '}';
    }
}
