package com.n26.utils;

import java.math.BigDecimal;

public final class BigDecimalUtils {

    private BigDecimalUtils() {
        
    }

    public static BigDecimal max(BigDecimal arg1, BigDecimal arg2) {
        if (arg1 == null && arg2 == null) {
            return BigDecimal.ZERO;
        } else if (arg1 == null) {
            return arg2;
        } else if (arg2 == null) {
            return arg1;
        } else {
            return arg1.max(arg2);
        }
    }

    public static BigDecimal min(BigDecimal arg1, BigDecimal arg2) {
        if (arg1 == null && arg2 == null) {
            return BigDecimal.ZERO;
        } else if (arg1 == null) {
            return arg2;
        } else if (arg2 == null) {
            return arg1;
        } else {
            return arg1.min(arg2);
        }
    }

    public static BigDecimal halfRoundUp(BigDecimal input){
        if(input == null){
            return BigDecimal.ZERO;
        }
        return input.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal divide(BigDecimal input1, BigDecimal input2){
        return halfRoundUp(input1.divide(input2));
    }

}
