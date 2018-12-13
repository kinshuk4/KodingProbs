package com.n26.utils;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalUtilsTest {

    @Test
    public void validate_max() {
        BigDecimal actual = new BigDecimal(5);
        BigDecimal expected = BigDecimalUtils.max(actual, null);
        Assert.assertEquals(expected, actual);
        expected = BigDecimalUtils.max(null, actual);
        Assert.assertEquals(expected, actual);
        BigDecimal greaterNumber =  new BigDecimal(10);
        expected = BigDecimalUtils.max(greaterNumber, actual);
        Assert.assertEquals(expected, greaterNumber);
    }
    
    @Test
    public void validate_min() {
        BigDecimal actual = new BigDecimal(5);
        BigDecimal expected = BigDecimalUtils.max(actual, null);
        Assert.assertEquals(expected, actual);
        expected = BigDecimalUtils.max(null, actual);
        Assert.assertEquals(expected, actual);
        BigDecimal greaterNumber =  new BigDecimal(10);
        expected = BigDecimalUtils.min(greaterNumber, actual);
        Assert.assertEquals(expected, actual);
    }

}
