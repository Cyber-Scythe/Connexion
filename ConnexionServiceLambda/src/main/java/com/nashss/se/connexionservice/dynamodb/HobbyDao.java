package com.nashss.se.connexionservice.dynamodb;

import com.nashss.se.connexionservice.dynamodb.models.Hobby;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.inject.Inject;

public class HobbyDao {

    private final DynamoDBMapper dynamoDbMapper;
    private final Logger log = LogManager.getLogger();

    /**
     * Instantiates an HobbyDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the categories table
     */
    @Inject
    public HobbyDao(DynamoDBMapper dynamoDbMapper) {

        this.dynamoDbMapper = dynamoDbMapper;
    }

    /**
     * Retrieves all hobbies in hobbies table.
     * <p>
     * If not found, throws HobbyNotFoundException.
     *
     * @return All hobbies in hobbies table
     */
    public List<Hobby> getHobbies() {
        log.info("Inside HobbyDao getHobbies");

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Hobby> hobbyScanExpression =  dynamoDbMapper.scan(Hobby.class, scanExpression);
        return hobbyScanExpression;
    }
}

