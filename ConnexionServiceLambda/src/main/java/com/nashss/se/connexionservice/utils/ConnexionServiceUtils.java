package com.nashss.se.connexionservice.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class ConnexionServiceUtils {

    private static final Pattern INVALID_CHARACTER_PATTERN = Pattern.compile("[\"'\\\\]");

    private ConnexionServiceUtils() {
    }

    public static boolean isValidString(String stringToValidate) {
        if (StringUtils.isBlank(stringToValidate)) {
            return false;
        } else {
            return !INVALID_CHARACTER_PATTERN.matcher(stringToValidate).find();
        }
    }

}

