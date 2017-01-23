package gojek.test;

import static gojek.test.constants.TestResponseStrings.threadPoolSize;

import gojek.test.threads.ParkingThreads;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vaani.gojek.entity.Slot;


/**
 * Tests to check for thread safety of API's
 * In current assignment , checking that when Parking any Vehicle , same slot should
 * not be assigned to 2  different cars. Locking in the actual API is only taken
 * when absolute necessary as Concurrency protection is a costly operation.
 * @author kchandra
 *
 */
public class MultiThreadingTests extends AbstractTest {

  private ExecutorService executorService;

  @Before
  public void setUp() throws Exception {

    executorService = Executors.newFixedThreadPool(threadPoolSize);

  }


  /**
   * Unit test to test the thread safety for parking the vehicle. If thread is not safe , 
   * it will try to park 2  vehicle in the same slot . To Test this , I have used breakpoints
   * to see if the lock is getting released by other object even before slot gets filled . 
   * 
   * @throws InterruptedException
   * @throws ExecutionException
   */
  @Test
  public void testParkingCarMultiThreaded() throws InterruptedException, ExecutionException {
    
    Future<Slot> f1 = executorService.submit(new ParkingThreads(gjParking,park,validCar));
    Future<Slot> f2 = executorService.submit(new ParkingThreads(gjParking,park,validCar2));
    Slot s1 = f1.get();
    Slot s2 = f2.get();
    assertNotNull(s1);
    assertNotNull(s2);
    assertNotEquals(s1.getId(),s2.getId());
                      
  }
  
  
  @After
  public void tearDown() throws Exception {

    executorService.shutdown();

  }
  
}
