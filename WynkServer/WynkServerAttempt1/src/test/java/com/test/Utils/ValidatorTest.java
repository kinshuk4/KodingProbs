package com.test.Utils;

import org.junit.Test;

import static com.trivago.pipeline.Utils.Validator.*;
import static junit.framework.Assert.assertEquals;


/**
 * Created by Chaklader on 11/20/16.
 */
public class ValidatorTest {

    // test the method for the holet rating
    @Test
    public void test_IsValidHotelRating_1() throws Exception {
        assertEquals(true, isValidHotelRating("4"));
    }

    @Test
    public void test_IsValidHotelRating_2() throws Exception {
        assertEquals(false, isValidHotelRating("78"));
    }

    @Test
    public void test_IsValidHotelRating_3() throws Exception {
        assertEquals(true, isValidHotelRating("0"));
    }

    @Test
    public void test_IsValidHotelRating_4() throws Exception {
        assertEquals(false, isValidHotelRating("seattle"));
    }

    @Test
    public void test_IsValidHotelRating_5() throws Exception {
        assertEquals(false, isValidHotelRating(""));
    }

    @Test
    public void test_IsValidHotelRating_6() throws Exception {
        assertEquals(false, isValidHotelRating(null));
    }



    // test the method for the uri verification
    // the first implementation
    @Test
    public void testIsUrlValidated_1() throws Exception {
        final String uri = "https://www.facebook.com/";
        assertEquals(true, isUrlValidated(uri));
    }

    @Test
    public void testIsUrlValidated_2() throws Exception {
        final String uri = "http://www.facebook.com/";
        assertEquals(true, isUrlValidated(uri));
    }

    @Test
    public void testIsUrlValidated_3() throws Exception {
        assertEquals(false, isUrlValidated(""));
    }

    @Test
    public void testIsUrlValidated_4() throws Exception {
        assertEquals(false, isUrlValidated(null));
    }

    // the second implementation
    @Test
    public void testIsUrlValidated_5() throws Exception {
        final String uri = "https://www.facebook.com/";
        assertEquals(true, isUrlValidated2(uri));
    }

    @Test
    public void testIsUrlValidated_6() throws Exception {
        final String uri = "http://www.facebook.com/";
        assertEquals(true, isUrlValidated2(uri));
    }

    @Test
    public void testIsUrlValidated_7() throws Exception {
        assertEquals(false, isUrlValidated2(""));
    }

    @Test
    public void testIsUrlValidated_8() throws Exception {
        assertEquals(false, isUrlValidated2(null));
    }


    // check whether the hotel name only contain UTF-8 characters
    @Test
    public void testIsNameIsUTF8_1() throws Exception {
        assertEquals(true, isNameIsUTF8("berlin"));
    }

    @Test
    public void testIsNameIsUTF8_2() throws Exception {
        assertEquals(true, isNameIsUTF8("deutsche bahn"));
    }

    @Test
    public void testIsNameIsUTF8_3() throws Exception {
        assertEquals(false, isNameIsUTF8(""));
    }

    @Test
    public void testIsNameIsUTF8_4() throws Exception {
        assertEquals(false, isNameIsUTF8(null));
    }
}