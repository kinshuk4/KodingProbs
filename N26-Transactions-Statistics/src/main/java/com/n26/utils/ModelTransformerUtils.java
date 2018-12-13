package com.n26.utils;

import com.n26.model.Statistics;
import com.n26.model.StatisticsSummary;
import com.n26.model.Transaction;
import com.n26.model.response.StatisticsResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.n26.utils.BigDecimalUtils.halfRoundUp;

public final class ModelTransformerUtils {
    private ModelTransformerUtils() {

    }

    public static StatisticsResponse convertStatisticsSummaryToResponse(StatisticsSummary statisticsSummary) {
        statisticsSummary.setAvg(halfRoundUp(statisticsSummary.getAvg()));
        statisticsSummary.setMax(halfRoundUp(statisticsSummary.getMax()));
        statisticsSummary.setMin(halfRoundUp(statisticsSummary.getMin()));
        statisticsSummary.setSum(halfRoundUp(statisticsSummary.getSum()));
        return StatisticsResponse.builder()
                .avg(statisticsSummary.getAvg().toPlainString())
                .count(statisticsSummary.getCount().longValue())
                .max(statisticsSummary.getMax().toPlainString())
                .min(statisticsSummary.getMin().toPlainString())
                .sum(statisticsSummary.getSum().toPlainString()).build();
    }

    public static Statistics convertTxToStatistics(Transaction tx, long transactionTimeInMillis) {
        Statistics value = Statistics.builder()
                .timestamp(transactionTimeInMillis)
                .sum(tx.getAmount())
                .max(tx.getAmount())
                .min(tx.getAmount())
                .count(BigDecimal.ONE)
                .build();
        return value;
    }

    public static Statistics addTxToStatistics(Statistics stats, Transaction tx) {
        return Statistics.builder()
                .timestamp(stats.getTimestamp())
                .count(stats.getCount().add(BigDecimal.ONE))
                .sum(stats.getSum().add(tx.getAmount()))
                .max(BigDecimalUtils.max(tx.getAmount(), stats.getMax()))
                .min(BigDecimalUtils.min(tx.getAmount(), stats.getMin()))
                .build();
    }

    public static StatisticsSummary convertStatisticsToSummary(Statistics stats){
        return new StatisticsSummary().fromStatistics(stats);
    }

    public static StatisticsSummary convertTxToSummary(Transaction tx, LocalDateTime ts){
        return convertTxToSummary(tx, ts.toEpochSecond(ZoneOffset.UTC));
    }
    public static StatisticsSummary convertTxToSummary(Transaction tx, long timeMillis){
        return convertStatisticsToSummary(convertTxToStatistics(tx, timeMillis));
    }



}
