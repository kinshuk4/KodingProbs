package com.vaani.gojek.parking.utils;

import com.vaani.gojek.entity.Parking;
import com.vaani.gojek.entity.Vehicle;
import com.vaani.gojek.parking.exceptions.GoJekException;

/**
 * Validate class to validate various parameters and throw exceptions accordingly. This will be used throughout the
 * project . It is done to make code more readable and remove duplicate code.
 * 
 * @author kchandra
 *
 */
public class ValidateInput {

  public boolean validateVehicle(Vehicle v) {
    if (v.getColor() == null || v.getColor().isEmpty() || v.getRegNumber() == null || v.getRegNumber().isEmpty()) {
      return false;
    }
    return true;
  }

  public void validateParking(Parking parking) throws GoJekException {
    if (parking == null) {
      throw new GoJekException("Parking lot is not created . Please create parking lot");
    }
  }

  public void validateSlotNumber(int slotNumber) throws GoJekException {
    if (slotNumber <= 0) {
      throw new GoJekException(" Invalid input .slotNumber cannot be less than 0");
    }
  }

  /**
   * check if color is Empty or null
   * 
   * @param value
   * @throws GoJekException 
   */
  public void validateColorOrRegNumber(String value) throws GoJekException {
    if (value == null || value.length() == 0) {
      throw new GoJekException("Color for vehicle cant be null or Empty");
    }

  }
}
