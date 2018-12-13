package com.n26.model;

import lombok.*;

import java.math.BigDecimal;

/**
 * Single Statistics object stored as cache value
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Statistics {
    private final long timestamp;
    private final BigDecimal sum;
    private final BigDecimal max ;
    private final BigDecimal min ;
    private final BigDecimal count;

}
