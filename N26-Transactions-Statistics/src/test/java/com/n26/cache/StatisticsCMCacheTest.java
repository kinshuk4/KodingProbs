package com.n26.cache;

import com.n26.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class StatisticsCMCacheTest {

    private StatisticsCMCache statisticsRepo = StatisticsCMCache.getInstance();

    @Before
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        statisticsRepo.clear();
    }

    @Test
    public void future_transaction_shouldBe_added() {

        LocalDateTime futureTs = LocalDateTime.now().plusSeconds(100);
        Transaction tx = new Transaction(new BigDecimal(120.34), futureTs);

        boolean actual = statisticsRepo.add(tx);

        Assert.assertTrue(actual);
        Assert.assertEquals(1, statisticsRepo.size());
    }

    @Test
    public void stale_transaction_shouldNotBe_added() {
        LocalDateTime staleTs = LocalDateTime.now().minusSeconds(100);
        Transaction tx = new Transaction(new BigDecimal(120.34), staleTs);

        boolean actual = statisticsRepo.add(tx);

        Assert.assertFalse(actual);
        Assert.assertEquals(0, statisticsRepo.size());
    }

    @Test
    public void nonStale_transaction_shouldBe_added() {
        LocalDateTime staleTs = LocalDateTime.now().minusSeconds(20);
        Transaction tx = new Transaction(new BigDecimal(120.34), staleTs);

        boolean actual = statisticsRepo.add(tx);

        Assert.assertTrue( actual);
        Assert.assertEquals(1, statisticsRepo.size());
    }

    @Test
    public void shouldNotGetAnythingAfter60Seconds() throws InterruptedException {
        LocalDateTime staleTs = LocalDateTime.now().minusSeconds(50);
        Transaction tx = new Transaction(new BigDecimal(120.34), staleTs);

        boolean actual = statisticsRepo.add(tx);

        Thread.sleep(11000);

        Assert.assertTrue( actual);
        Assert.assertEquals(BigDecimal.ZERO, statisticsRepo.getLast60SecondsStatistics().getCount());
    }
}