package com.n26.service.impl;

import com.n26.model.StatisticsSummary;
import com.n26.model.Transaction;
import com.n26.service.IStatisticsService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.n26.constant.Constants.STATS_AGGREGATION_TIME_MILLIS;
import static com.n26.utils.BigDecimalUtils.divide;
import static com.n26.utils.BigDecimalUtils.halfRoundUp;
import static com.n26.utils.ModelTransformerUtils.convertTxToSummary;
import static org.junit.Assert.assertEquals;

public class StatisticsServiceImplTest {

    private final IStatisticsService statisticsService = new StatisticsServiceImpl();

    private static final int poolSize = 16;
    ExecutorService executor;

    @Before
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        MockitoAnnotations.initMocks(this);
        statisticsService.removeAllStatistics();
        executor = Executors.newFixedThreadPool(poolSize);
    }

    @After
    public void destroy() {
        executor.shutdown();
    }


    @Test
    public void future_transaction_shouldBe_added() {
        LocalDateTime futureTs = LocalDateTime.now().plusSeconds(100);
        Transaction tx = new Transaction(new BigDecimal(120.34), futureTs);
        statisticsService.updateStatistics(tx);
        StatisticsSummary statistics = statisticsService.getStatistics();
        StatisticsSummary expected = convertTxToSummary(tx, futureTs);
        Assert.assertEquals(expected, statistics);
    }

    @Test
    public void stale_transaction_shouldNotBe_added() {
        LocalDateTime staleTs = LocalDateTime.now().minusSeconds(100);
        Transaction tx = new Transaction(new BigDecimal(120.34), staleTs);
        statisticsService.updateStatistics(tx);
        StatisticsSummary statistics = statisticsService.getStatistics();
        assertNoStatisticsPresent(statistics);
    }

    @Test
    public void valid_transactions_add_sequentially() {
        Random rand = new Random();
        int time = 0;
        double amount = rand.nextInt(500) + 1;
        double sum = 0;
        int count = 200;
        double min = amount, max = amount;
        for (int i = 0; i < count; i++) {
            time = rand.nextInt(50) + 1;
            amount = rand.nextInt(500) + 1;
            sum += amount;
            if (min > amount) {
                min = amount;
            }
            if (max < amount) {
                max = amount;
            }
            statisticsService.updateStatistics(new Transaction(new BigDecimal(amount), LocalDateTime.now().minusSeconds(time)));
        }

        StatisticsSummary statistics = statisticsService.getStatistics();
        assertBigDecimalsEqual(divide(new BigDecimal(sum), new BigDecimal(count)), statistics.getAvg());
        assertBigDecimalsEqual(new BigDecimal(min), statistics.getMin());
        assertBigDecimalsEqual(new BigDecimal(max), statistics.getMax());
        assertBigDecimalsEqual(new BigDecimal(sum), statistics.getSum());
        assertBigDecimalsEqual(new BigDecimal(count), statistics.getCount());
    }

    @Test
    public void no_statistics_available_after_60seconds() throws InterruptedException {
        Transaction tx = new Transaction(new BigDecimal(120.34), LocalDateTime.now().minusSeconds(50));
        statisticsService.updateStatistics(tx);
        TimeUnit.SECONDS.sleep(10);
        StatisticsSummary statistics = statisticsService.getStatistics();
        assertNoStatisticsPresent(statistics);
    }

    @Test
    public void no_statistics_available_after_deletion_of_transactions() {
        Transaction tx = new Transaction(new BigDecimal(120.34), LocalDateTime.now().minusSeconds(50));
        statisticsService.updateStatistics(tx);
        statisticsService.removeAllStatistics();
        StatisticsSummary statistics = statisticsService.getStatistics();
        assertNoStatisticsPresent(statistics);
    }

    @Test
    public void valid_concurrent_transactions_added_correctly() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        int txCount = STATS_AGGREGATION_TIME_MILLIS;

        List<Callable<Void>> callables = getCallables(txCount, now);

        executor.invokeAll(callables);

        BigDecimal sum = statisticsService.getStatistics().getSum();
        assertEquals(new BigDecimal(txCount), sum);
    }

    @Test
    public void valid_concurrent_transactions_discards_old_transactions() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now().minusSeconds(1);
        int txCount = STATS_AGGREGATION_TIME_MILLIS;

        List<Callable<Void>> callables = getCallables(txCount, now);

        executor.invokeAll(callables);
        Thread.sleep(1000);
        BigDecimal sum = statisticsService.getStatistics().getSum();
        assertEquals(new BigDecimal(txCount), sum);
    }

    @Test
    public void valid_concurrent_transactions_addup_sequentially_as_well() throws InterruptedException {

        LocalDateTime now = LocalDateTime.now();

        int txCount = STATS_AGGREGATION_TIME_MILLIS;

        List<Callable<Void>> callables = getCallables(txCount, now);

        executor.invokeAll(callables);
        callables.clear();

        Transaction tx = new Transaction(BigDecimal.ONE, now.plusNanos((txCount + 1) * 1000));
        callables.add(() -> {
            statisticsService.updateStatistics(tx);
            return null;
        });
        executor.invokeAll(callables);

        BigDecimal sum = ((StatisticsServiceImpl) statisticsService).getStatistics().getSum();
        assertEquals(new BigDecimal(txCount + 1), sum);
    }

    private List<Callable<Void>> getCallables(int numCallables, LocalDateTime now) {
        List<Callable<Void>> callables = new ArrayList<>();

        for (int i = 0; i < numCallables; i++) {
            Transaction tx = new Transaction(BigDecimal.ONE, now.plusNanos(i * 1000));
            callables.add(() -> {
                statisticsService.updateStatistics(tx);
                return null;
            });
        }
        return callables;
    }

    private void assertNoStatisticsPresent(StatisticsSummary statistics) {
        Assert.assertEquals(BigDecimal.ZERO, statistics.getAvg());
        Assert.assertEquals(BigDecimal.ZERO, statistics.getMin());
        Assert.assertEquals(BigDecimal.ZERO, statistics.getMax());
        Assert.assertEquals(BigDecimal.ZERO, statistics.getSum());
        Assert.assertEquals(BigDecimal.ZERO, statistics.getCount());
    }

    private void assertBigDecimalsEqual(BigDecimal input1, BigDecimal input2) {
        Assert.assertEquals(halfRoundUp(input1), halfRoundUp(input2));
    }
}
