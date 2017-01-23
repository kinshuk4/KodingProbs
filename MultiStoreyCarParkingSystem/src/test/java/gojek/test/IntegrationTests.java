package gojek.test;

import static gojek.test.constants.TestResponseStrings.allocatedSlotOne;
import static gojek.test.constants.TestResponseStrings.color;
import static gojek.test.constants.TestResponseStrings.color2;
import static gojek.test.constants.TestResponseStrings.createParkingFailure;
import static gojek.test.constants.TestResponseStrings.parkingLotFull;
import static gojek.test.constants.TestResponseStrings.createParkingSuccess;
import static gojek.test.constants.TestResponseStrings.lastSlotFreeSuccessMessage;
import static gojek.test.constants.TestResponseStrings.slotFreeFailureMessage;
import static gojek.test.constants.TestResponseStrings.slotFreeSuccessMessage;
import static gojek.test.constants.TestResponseStrings.validRegNo;
import static gojek.test.constants.TestResponseStrings.validRegNo2;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.vaani.gojek.entity.MultiStoreyParking;
import com.vaani.gojek.entity.type.VehicleType;
import com.vaani.gojek.parking.exceptions.GoJekException;
import com.vaani.gojek.parking.orchestrator.Orchestrator;

/**
 * Integration tests to test features end to end
 * 
 * @author kchandra
 *
 */
public class IntegrationTests extends AbstractTest {

  /**
   * Positive Integration test case for checking creation of Parking lot returns correct message or not
   * 
   * @throws GoJekException
   */
  @Test
  public void integrationTestParkingCreationSuccessMessage() throws GoJekException {
    String resp = or.createParkingLot(6);
    assertEquals(createParkingSuccess, resp);
  }

  /**
   * Negative Integration test case for checking failed creation of Parking lot returns correct message or not
   * 
   * @throws GoJekException
   */
  @Test
  public void integrationTestParkingCreationFailedMessage() throws GoJekException {
    String resp = or.createParkingLot(0);
    assertEquals(createParkingFailure, resp);
  }

  /**
   * Positive Integration test case for checking leave command returns correct message or not
   * 
   * @throws GoJekException
   */
  @Test
  public void integrationTestParkingFreeSuccessMessage() throws GoJekException {
    String resp = or.freeSlot(2);
    assertEquals(slotFreeSuccessMessage, resp);
  }

  /**
   * Positive Integration test case for checking leave command returns correct message or not when slotNumber is equal
   * to capacity
   * 
   * @throws GoJekException
   */
  @Test
  public void integrationTestLastSlotParkingFreeSuccessMessage() throws GoJekException {
    String resp = or.freeSlot(5);
    assertEquals(lastSlotFreeSuccessMessage, resp);
  }

  /**
   * Negative Integration test case for checking leave command returns correct message or not when slotNumber is greater
   * to capacity
   * 
   * @throws GoJekException
   */
  @Test
  public void integrationTestParkingFreeFailureMessage() throws GoJekException {
    String resp = or.freeSlot(6);
    assertEquals(slotFreeFailureMessage, resp);
  }

  /**
   * Negative Integration test case for checking if vehicle parking returns correct message or not when first slotNumber
   * is available
   * 
   * @throws GoJekException
   */
  @Test
  public void integrationTestParkingVehicleFailure() throws GoJekException {
    MultiStoreyParking p = new MultiStoreyParking(1);
    Orchestrator or1 = new Orchestrator(p);
    String resp = or1.ParkVehicle(validRegNo, color, VehicleType.Car);
    assertEquals(allocatedSlotOne, resp);
    String resp2 = or1.ParkVehicle(validRegNo2, color2, VehicleType.Car);
    assertEquals(parkingLotFull, resp2);
  }

  /**
   * Positive Integration test case for checking if vehicle parking returns correct message or not when first slotNumber
   * is available
   * 
   * @throws GoJekException
   */
  @Test
  public void integrationTestParkingVehicleSuccessful() throws GoJekException {
    String resp = or.ParkVehicle(validRegNo, color, VehicleType.Car);
    assertEquals(allocatedSlotOne, resp);
  }

}
