package com.nashss.se.connexionservice.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * PARTICIPANTS: The class should NOT be touched when working on the project!
 * DynamoDBMapper converts lists to {@link java.util.ArrayList}s by default, but for our project,
 * we want to convert to a {@link LinkedList}
 */
public class StringConverter implements DynamoDBTypeConverter<String, List> {
    private static final Gson GSON = new Gson();
    private final Logger log = LogManager.getLogger();

    @Override
    public String convert(List listToBeConverted) {
        return GSON.toJson(listToBeConverted);
    }

    @Override
    public List unconvert(String dynamoDbRepresentation) {
        // need to provide the type parameter of the list to convert correctly
        return GSON.fromJson(dynamoDbRepresentation, new TypeToken<LinkedList<String>>() { } .getType());
    }
}
