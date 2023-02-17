package com.nashss.se.connexionservice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    public Date convertStringToDateTime(String dateTimeSent) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy;HH:mm:ss");
            Date dateTime = sf.parse(dateTimeSent);

            System.out.println(dateTime);
            return dateTime;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
