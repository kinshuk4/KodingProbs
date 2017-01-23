package gojek.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static gojek.test.constants.TestResponseStrings.validRegNo;
import static gojek.test.constants.TestResponseStrings.invalidRegNo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.vaani.gojek.entity.Parking;
import com.vaani.gojek.parking.exceptions.GoJekException;

/**
 * Unit tests for get API's i.e. (getSlotNumbersForColor,getRegistrationNumbers,getSlotNumbersForRegistrationNumbers)
 * 
 * @author kchandra
 *
 */
public class GetParkingTests extends AbstractTest {
    
  /**
   * Positive Test Case to get All Slot numbers of particular color
   * 
   * @throws GoJekException
   */
  @Test
  public void testGetSlotNumbersForColorPositive() throws GoJekException {
    Parking  p = mp.mockMultiStoreyParkingWithFilledSlots();
    List<Integer> mockSlotListWhite = mp.getMockSlotListWhite();
    List<Integer> slotList = gjParking.getSlotNumbersForColor(p, "White");
    assertNotNull(slotList);
    assertEquals(mockSlotListWhite.size(),slotList.size());
    assertTrue(slotList.equals(mockSlotListWhite));
  }
  
  
  /**
   * Positive Test Case to get All Slot numbers of particular color
   * 
   * @throws GoJekException
   */
  @Test
  public void testGetSlotNumbersForColorPositive2() throws GoJekException {
    Parking  p = mp.mockMultiStoreyParkingWithFilledSlots();
    List<Integer> mockSlotListBlack = mp.getMockSlotListBlack();
    List<Integer> slotList = gjParking.getSlotNumbersForColor(p, "Black");
    assertNotNull(slotList);
    assertEquals(mockSlotListBlack.size(),slotList.size());
    assertTrue(slotList.equals(mockSlotListBlack));
  }
  
  /**
   * Positive Test Case to get All Slot numbers of particular color
   * for which there are no cars 
   * 
   * @throws GoJekException
   */
  @Test
  public void testGetSlotNumbersForColorSizeZeroPositive() throws GoJekException {
    Parking  p = mp.mockMultiStoreyParkingWithFilledSlots();
    
    List<Integer> slotList = gjParking.getSlotNumbersForColor(p, "Blue");
    assertNotNull(slotList);
    assertEquals(0,slotList.size());
  }
  
  /**
   * Negative Test Case to get All Slot numbers of particular color
   * with null parking
   * @throws GoJekException
   */
  @Test(expected = GoJekException.class)
  public void testGetSlotNumbersForColorNegative() throws GoJekException {
   gjParking.getSlotNumbersForColor(null, "White");
  }
  
  /**
   * Negative Test Case to get All Slot numbers of particular color
   * with null color
   * 
   * @throws GoJekException
   */
  @Test(expected = GoJekException.class)
  public void testGetSlotNumbersForColorNullColorNegative() throws GoJekException {
   gjParking.getSlotNumbersForColor(park, null);
  }
  
  /**
   * Positive Test Case to get All registration numbers of particular color 
   * 
   * @throws GoJekException
   */
  @Test
  public void testGetRegistrationNumbersPositive() throws GoJekException {
    Parking  p = mp.mockMultiStoreyParkingWithFilledSlots();
    ArrayList<String> regList  = gjParking.getRegistrationNumbers(p, "White");
    Map<String, ArrayList<String>> mockRegMap = mp.getMockRegMap();
    ArrayList<String> mockRegList = mockRegMap.get("White");
    assertNotNull(regList);
    assertEquals(mockRegList,regList);
  }
  
  /**
   * Negative Test Case to get All registration numbers of particular color with Parking lot not available/created
   * 
   * @throws GoJekException
   */
  @Test(expected = GoJekException.class)
  public void testGetRegistrationNumbersNegative() throws GoJekException {
    gjParking.getRegistrationNumbers(null, "White");
  }

  /**
   * Negative Test Case to get All registration numbers of particular color
   * 
   * @throws GoJekException
   */
  @Test(expected = GoJekException.class)
  public void testGetRegistrationNumbersNullColorNegative() throws GoJekException {
    gjParking.getRegistrationNumbers(park, null);
  }
  
  

  @Test
  public void getSlotNumberForRegistrationNumberPositive() throws GoJekException {
    Parking  p = mp.mockMultiStoreyParkingWithFilledSlots();
    int slotNumber = gjParking.getSlotNumberForRegistrationNumber(p, validRegNo);
    assertEquals(1,slotNumber);
  }
  
  
  @Test
  public void getSlotNumberForRegistrationNumberNegativeRegNoNotPresent() throws GoJekException {
    Parking  p = mp.mockMultiStoreyParkingWithFilledSlots();
    int slotNumber = gjParking.getSlotNumberForRegistrationNumber(p, invalidRegNo);
    assertEquals(-1,slotNumber);
  }
  
  /**
   * Negative Test Case to get All registration numbers of particular color with Parking lot not available/created
   * 
   * @throws GoJekException
   */
  @Test(expected = GoJekException.class)
  public void getSlotNumberForRegistrationNumberParkingNullNegative() throws GoJekException {
    gjParking.getRegistrationNumbers(null, "White");
  }

  /**
   * Negative Test Case to get All registration numbers of particular color
   * 
   * @throws GoJekException
   */
  @Test(expected = GoJekException.class)
  public void getSlotNumberForRegistrationNumberColorNullNegative() throws GoJekException {
    gjParking.getRegistrationNumbers(park, null);
  }

  
  
}
