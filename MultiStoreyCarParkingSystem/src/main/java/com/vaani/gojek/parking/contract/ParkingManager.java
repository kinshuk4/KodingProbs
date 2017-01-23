package com.vaani.gojek.parking.contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vaani.gojek.entity.Parking;
import com.vaani.gojek.entity.ParkingStatus;
import com.vaani.gojek.entity.Slot;
import com.vaani.gojek.entity.Vehicle;

/**
 * 
 * @author kchandra
 *
 */
public interface ParkingManager {

	public Slot findNearestEmptySlot(Parking parking);

	public void fillParkingSlot(Slot slot, Vehicle vehicle, Parking parking);

	public boolean freeParkingSlot(int slotNumber, Parking parking);
	
	public List<ParkingStatus> populateParkingStatus(Parking parking);

  public Map<String, ArrayList<String>> populateRegMap(Vehicle vehicle, Parking parking);

  public ArrayList<String> getRegistrationNumbersList(Parking parking, String color);

  /**
   * Having same Method for gettingSlotNumbers for Color or RegNumber as except 
   * the return type everything is same. In case of slots , return type will be list
   * whereas in regNumber it will be a single Integer .
   * This implementation is internal and so code duplicacy is not recommended .
   * This is different than goJekParkingImpl which contains APIs which will be consumed by
   * users of this library. So duplicacy is done there.
   * @param parking
   * @param value
   * @return
   */
  public List<Integer> getSlotNumbersForColor(Parking parking, String color);
  
  public Integer getSlotNumbersForRegNumber(Parking parking, String regNumber);




}
