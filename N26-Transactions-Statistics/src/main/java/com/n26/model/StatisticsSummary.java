package com.n26.model;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StatisticsSummary {
    private BigDecimal sum = new BigDecimal(0.0);
    private BigDecimal count = new BigDecimal(0l);
    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal avg;

    public StatisticsSummary fromStatistics(Statistics statistics) {
        this.count = statistics.getCount();
        this.max = statistics.getMax();
        this.min = statistics.getMin();
        this.sum = statistics.getSum();
        this.avg = statistics.getCount().compareTo(BigDecimal.ZERO) > 0 ? statistics.getSum().divide(statistics.getCount(), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
        return this;
    }

}
