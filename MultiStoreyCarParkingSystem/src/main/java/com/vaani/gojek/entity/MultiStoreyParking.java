package com.vaani.gojek.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.vaani.gojek.entity.type.ParkingLotType;

/**
 * 
 * Parking Lot Implementation for managing the parking positions of Vehicles as given in the problem statement .
 * 
 * @author kchandra
 *
 */
public class MultiStoreyParking extends Parking {

  private Slot[] slots;// describes an array of Parking slots.
  private int capacity;

  /**
   * There are more than 1 design choice here . Whether to populate and keep this map here inside this parking Object or
   * to build this separately inside MultiStoreyParkingImpl with just passing a populated Parking Object. I have gone
   * with the choice of keeping it inside this object and populating every-time a vehicle is parked . Reason being the
   * population logic is simple and though park Vehicle will take a little more time but overall it will still be less
   * when separate API
   */
  private Map<String, ArrayList<String>> regNumberMap;

  /**
   * constructor to create a Parking slot for the system. Invoked for call create_parking_lot n
   * 
   * @param n
   */
  public MultiStoreyParking(int n) {
    super(ParkingLotType.SingleFloor);
    this.slots = new Slot[n];
    for (int i = 0; i < n; i++) {
      this.slots[i] = new Slot(i + 1);
    }
    this.capacity = n;
    this.regNumberMap = new HashMap<String, ArrayList<String>>();
  }

  public Slot[] getSlots() {
    return slots;
  }

  public void setSlots(Slot[] slots) {
    this.slots = slots;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public Map<String, ArrayList<String>> getRegNumberMap() {
    return regNumberMap;
  }

  public void setRegNumberMap(Map<String, ArrayList<String>> regNumberMap) {
    this.regNumberMap = regNumberMap;
  }

}
