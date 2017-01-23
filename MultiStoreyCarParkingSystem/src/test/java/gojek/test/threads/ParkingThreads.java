package gojek.test.threads;

import java.util.concurrent.Callable;

import com.vaani.gojek.entity.Parking;
import com.vaani.gojek.entity.Slot;
import com.vaani.gojek.entity.Vehicle;
import com.vaani.gojek.parking.contract.GoJekParking;
import com.vaani.gojek.parking.exceptions.GoJekException;

/**
 * Test Class to help unit test the multithreading functionalities.
 * 
 * @author kchandra
 *
 */
public class ParkingThreads implements Callable<Slot> {

  private GoJekParking gjParking;
  private Parking park;
  private Vehicle v;

  public ParkingThreads() {

  }

  public ParkingThreads(GoJekParking gjParking, Parking park, Vehicle validCar) {
    this.gjParking = gjParking;
    this.park = park;
    this.v = validCar;
  }

  @Override
  public Slot call() {
    Slot s  = null;
    try {
       s = this.gjParking.ParkVehicle(this.park, this.v);
   } catch (GoJekException e) {
     e.printStackTrace();
   }
    return s;
  }
}
