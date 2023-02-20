package com.nashss.se.connexionservice.utils;

import com.nashss.se.connexionservice.dynamodb.models.Message;

import java.time.LocalDateTime;
import java.util.Comparator;

public class LocalDateTimeComparator implements Comparator<Message> {

    @Override
    public int compare(Message msg1, Message msg2) {
        return LocalDateTime.parse(msg1.getDateTimeSent()).compareTo(LocalDateTime.parse(msg2.getDateTimeSent()));
    }
}
