package gojek.test;

import static gojek.test.constants.TestResponseStrings.color;
import static gojek.test.constants.TestResponseStrings.validRegNo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.vaani.gojek.entity.Car;
import com.vaani.gojek.entity.Parking;
import com.vaani.gojek.entity.ParkingStatus;
import com.vaani.gojek.entity.Slot;
import com.vaani.gojek.entity.Vehicle;
import com.vaani.gojek.parking.exceptions.GoJekException;

/**
 * Unit Tests to test various methods
 * 
 * @author kchandra
 *
 */
@SuppressWarnings("unused")
public class ParkingTests extends AbstractTest {

	/**
	 * Negative test case to check if parking lot is created of 0 size
	 * 
	 * @throws GoJekException
	 * 
	 */
	@Test(expected = GoJekException.class)
	public void testEmptyParkingLotCreation() throws GoJekException {
		Parking p = gjParking.createParkingLot(0);
		assertNull(p);
	}

	/**
	 * Negative test case to check if parking lot is created of negative size
	 * 
	 * @throws GoJekException
	 * 
	 */
	@Test(expected = GoJekException.class)
	public void testNegativeParkingLotCreation() throws GoJekException {
		Parking p = gjParking.createParkingLot(-5);
		assertNull(p);
	}

	/**
	 * Positive test case for checking creation of Parking lot with non negative
	 * and non zero Integer
	 * 
	 * @throws GoJekException
	 */
	@Test
	public void testNonEmptyParkingLotCreation() throws GoJekException {
		Parking p = gjParking.createParkingLot(10);
		assertNotNull(p);
	}

	/**
	 * Positive test case to test Parking a car with regNo and Color
	 * 
	 * @throws GoJekException
	 */
	@Test
	public void testParkingCar() throws GoJekException {
		Slot s = gjParking.ParkVehicle(park, validCar);
		assertNotNull(s);

	}
	
	
	/**
	 * Negative test case to test Parking a Car with Color Empty Checking if the
	 * exception String is matching here or not
	 */
	@Test
	public void testParkingCarNullColor() {
		Vehicle vehicleEmptyColor = new Car(validRegNo, null);
		String message = null;
		try {
			Slot s = gjParking.ParkVehicle(park, vehicleEmptyColor);
		} catch (GoJekException e) {
			message = e.getMessage();
		}
		assertNotNull(message);
		//assertEquals(message, invalidCarExceptionMessage);
	}

	/**
	 * Negative test case to test Parking a Car with RegNo Not Passed Checking
	 * if the exception String is matching here or not
	 * 
	 * @throws GoJekException
	 */
	@Test
	public void testParkingCarNullRegNo() {
		Vehicle vehicleEmptyRegNo = new Car(null, color);
		String message = null;
		try {
			Slot s = gjParking.ParkVehicle(park, vehicleEmptyRegNo);
		} catch (GoJekException e) {
			message = e.getMessage();
		}
		assertNotNull(message);
		//assertEquals(message, invalidCarExceptionMessage);
	}

	/**
	 * Negative test case to test Parking a Car with Color Empty Checking if the
	 * Alternative and better(visually) way to test the last 2 test cases. 
	 * @throws GoJekException
	 */
	@Test(expected = GoJekException.class)
	public void testParkingCarEmptyColor() throws GoJekException {
		Vehicle vehicleEmptyColor = new Car(validRegNo, "");
	    gjParking.ParkVehicle(park, vehicleEmptyColor);
	}

	/**
	 * Negative test case to test Parking a Car with RegNo Not Passed Checking
	 * 
	 * @throws GoJekException
	 */
	@Test(expected = GoJekException.class)
	public void testParkingCarEmptyRegNo() throws GoJekException {
		Vehicle vehicleEmptyRegNo = new Car("", color);
		gjParking.ParkVehicle(park, vehicleEmptyRegNo);
	}

	/**
	 * Negative test case to test Parking a Car with Null Parking object
	 * 
	 * @throws GoJekException
	 */
	@Test(expected = GoJekException.class)
	public void testParkingCarNullParking() throws GoJekException {
		gjParking.ParkVehicle(null, validCar);
	}
	
	/**
	 * Positive test case to test parking for a car with Slot available
	 * @throws GoJekException 
	 */
	@Test
	public void testParkingPositiveSlotAvailable() throws GoJekException {
		Slot s = gjParking.ParkVehicle(park, validCar);
		assertNotNull(s);
		assertEquals(1, s.getId());
		Vehicle validCar2  = new Car("DL-­12-­AA-­9999","White");
		s = gjParking.ParkVehicle(park, validCar2);
		assertNotNull(s);
		assertEquals(2,s.getId());
		
	}
	
	/**
	 * Positive test case to test parking for a car with Slot unavailable/Parking Full
	 * @throws GoJekException 
	 */
	@Test
	public void testParkingPositiveParkingFull() throws GoJekException {
		Parking p  =  gjParking.createParkingLot(1);
		Slot s = gjParking.ParkVehicle(p, validCar);
		assertNotNull(s);
		assertEquals(1, s.getId());
		Vehicle validCar2  = new Car("DL-­12-­AA-­9999","White");
		s = gjParking.ParkVehicle(p, validCar2);
		assertNull(s);
		
	}
	
	/**
	 * Negative Test Case to test freeing of slot with negative test case
	 * @throws GoJekException
	 */
	@Test(expected=GoJekException.class)
	public void testFreeSlotInvalidSlotNumber() throws GoJekException {
		gjParking.freeParkingSlot(park, -1);
		
	}
	
	/**
	 * Positive Test Case to test freeing of slot with negative test case
	 * @throws GoJekException
	 */
	@Test
	public void testFreeSlotValidSlotNumberAndParkingCapacity() throws GoJekException {
		Parking  p = mp.mockMultiStoreyParkingWithFilledSlots();
		boolean res = gjParking.freeParkingSlot(p, 1);
		assertTrue(res);
	}
	
	/**
	 * Negative Test Case to free Slot with Parking lot not available/created
	 * @throws GoJekException
	 */
	@Test(expected=GoJekException.class)
	public void testFreeSlotParkingNotAvailable() throws GoJekException {
		gjParking.freeParkingSlot(null, 1);
		
	}
	
	
	/**
	 * Negative Test Case to print Parking Status with Parking lot not available/created
	 * @throws GoJekException
	 */
	@Test(expected=GoJekException.class)
	public void testPrintParkingStatusNegative() throws GoJekException {
		gjParking.getStatusforParking(null);
	}
	
	
	/**
	 * Positive Test Case to print Parking Status with Mock Parking lot 
	 * @throws GoJekException
	 */
	@Test
	public void testPrintParkingStatusPositive() throws GoJekException {
		Parking  p = mp.mockMultiStoreyParkingWithFilledSlots();
		List<ParkingStatus> psList = gjParking.getStatusforParking(p);
		List<ParkingStatus> mockPsList = mp.getPsList();
		assertNotNull(psList);
		assertEquals(psList.size(),mockPsList.size());
		assertTrue(psList.equals(mockPsList));
	}
	
	
	
}
