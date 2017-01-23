package gojek.test.constants;

/**
 * Strings as expected in response by cli or File Based Output
 * @author kchandra
 *
 */
public class TestResponseStrings {

	public final static String createParkingSuccess = "Created a parking lot with 6 slots";
	public final static String createParkingFailure = "Could not create Parking lot";
	public final static String validRegNo = "KA-­01-­HH-­1234";
	public final static String invalidRegNo = "KA-­10-­YY-2349";
	public final static String validRegNo2 = "KA-­02-­AA-­2345";
	public final static String color = "White";
	public final static String color2 = "Black";
	public final static String invalidCarExceptionMessage = "Invalid Car Object.";
	public final static String slotFreeSuccessMessage  = "Slot number 2 is free";
	public final static String lastSlotFreeSuccessMessage  = "Slot number 5 is free";
	public final static String slotFreeFailureMessage  = "Slot Number could not be freed . Slot Number given is invalid.";
	public final static String allocatedSlotOne = "Allocated slot number: 1";
	public final static String parkingLotFull = "Sorry, parking lot is full";
	public final static int threadPoolSize = 3;
	
	
}
