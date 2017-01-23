package com.vaani.gojek.parking.contract;

import java.util.ArrayList;
import java.util.List;

import com.vaani.gojek.entity.Parking;
import com.vaani.gojek.entity.ParkingStatus;
import com.vaani.gojek.entity.Slot;
import com.vaani.gojek.entity.Vehicle;
import com.vaani.gojek.parking.exceptions.GoJekException;

/**
 * Interface/Contract for exposing the API's which will be used to provide functionalities the problem expects. This is
 * consumer facing API. Since the current problem expects a command line and file based input , I have introduced an
 * additional layer to Orchestrate the command line and file base input and covert it to Parking. This is done to
 * provide loose coupling as tomorrow instead of command line input , one may wish to expose this software as
 * library(jar) or even a service over web . Current design supports both these or more modifications. Creating a single
 * Contract method means clients do not break if we add more varieties of parking in Future . Alternatively, one can
 * also expose different types of API's to support different types or parking (to generate revenue based on API's) .
 * That decision depends on requirements and product management. I have gone with the below from an engineer point of
 * view.
 * 
 * @author kchandra
 *
 */
public interface GoJekParking {

  /**
   * Solution assumes that multiple instances of ParkingLot can be created . Else 
   * we can look at wrapping Parking Object inside a singleton.
   * @param n
   * @return
   * @throws GoJekException
   */
  public Parking createParkingLot(int n) throws GoJekException;

  /**
   * Method exposed to parkVehicle. This expects a valid Parking lot created. Vehicle with invalid or empty Reg no will
   * throw exception.
   * 
   * @param p
   * @param v
   * @throws GoJekException
   */
  public Slot ParkVehicle(Parking p, Vehicle v) throws GoJekException;

  public List<ParkingStatus> getStatusforParking(Parking p) throws GoJekException;

  public boolean freeParkingSlot(Parking p, int slotId) throws GoJekException;

  public ArrayList<String> getRegistrationNumbers(Parking p, String color) throws GoJekException;

  /**
   * Api for gettingSlotNumbers for given Color.
   * @param p
   * @param color
   * @return
   * @throws GoJekException
   */
  public List<Integer> getSlotNumbersForColor(Parking p, String color) throws GoJekException;

  
  /**
   * Api for gettingSlotNumbers for given Registration Number. Please see that I have
   * introduced different API's for getting SlotNumber for color and separate for RegNumber.
   * Rationale behind keeping them separate is that I assume customer will be consuming them  so keeping
   * them separate makes sense. Though it does introduce some duplicacy in code , considering from end
   * user perspective , I have gone with this choice of implementation.
   * @param p
   * @param regNumber
   * @return
   * @throws GoJekException
   */
  public Integer getSlotNumberForRegistrationNumber(Parking p, String regNumber) throws GoJekException;

}
