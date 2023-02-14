package com.nashss.se.connexionservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.nashss.se.connexionservice.dynamodb.models.Hobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HobbyDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    private HobbyDao hobbyDao;
    @Mock
    private PaginatedScanList<Hobby> scanResult;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        hobbyDao = new HobbyDao(dynamoDBMapper);
    }

    @Test
    void getHobby_scansDynamoDbHobbies_returnsHobbies() {
        // GIVEN
        when(dynamoDBMapper.scan(eq(Hobby.class), any())).thenReturn(scanResult);

        ArgumentCaptor<DynamoDBScanExpression> scanExpressionArgumentCaptor =
                ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        // WHEN
        List<Hobby> hobbies = hobbyDao.getHobbies();

        // THEN
        verify(dynamoDBMapper).scan(eq(Hobby.class), scanExpressionArgumentCaptor.capture());
        DynamoDBScanExpression scanExpression = scanExpressionArgumentCaptor.getValue();

        verify(dynamoDBMapper).scan(Hobby.class, scanExpression);

        assertEquals(scanResult, hobbies, "Expected method to return the results of the scan");

        System.out.println("hobbies: " + hobbies.toString());
        System.out.println("scan result: " + scanResult);
    }
}
