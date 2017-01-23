package com.trivago.pipeline.Utils;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.UnsupportedEncodingException;

/**
 * Created by Chaklader on 11/20/16.
 */
public class Validator {

    public static boolean isValidHotelRating(String stars) {

        if(stars == null || stars.isEmpty())
            return false;

        int hotelRating;
        try {
            hotelRating = Integer.parseInt(stars);
        }

        catch (NumberFormatException e) {
            return false;
        }

        if (hotelRating >= 0 && hotelRating <= 5)
            return true;

        return false;
    }

    public static boolean isUrlValidated(String uri) {

        if(uri == null || uri.isEmpty())
            return false;

        UrlValidator urlValidator = new UrlValidator();

        if (urlValidator.isValid(uri))
            return true;

        else
            return false;
    }

    // validate the uri using the regular expression
    public static boolean isUrlValidated2(String uri) {

        if(uri == null || uri.isEmpty())
            return false;

        final String REGEX_URL = "^[A-Za-z][A-Za-z0-9+.-]{1,120}:[A-Za-z0-9/](([A-Za-z0-9$_.+!*,;/?:@&~=-])" +
                "|%[A-Fa-f0-9]{2}){1,333}(#([a-zA-Z0-9][a-zA-Z0-9$_.+!*,;/?:@&~=%-]{0,1000}))?$";

        return uri.matches(REGEX_URL);
    }


    // check if the name of the hotel is valid UTF-8 encoded
    public static boolean isNameIsUTF8(String s) {

        if(s == null || s.isEmpty())
            return false;

        byte[] bytes;
        try {
            bytes = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        return bytes.length != 0;
    }

}
