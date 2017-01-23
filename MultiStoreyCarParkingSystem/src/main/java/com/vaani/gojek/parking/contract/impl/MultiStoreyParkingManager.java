package com.vaani.gojek.parking.contract.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vaani.gojek.entity.MultiStoreyParking;
import com.vaani.gojek.entity.Parking;
import com.vaani.gojek.entity.ParkingStatus;
import com.vaani.gojek.entity.Slot;
import com.vaani.gojek.entity.Vehicle;
import com.vaani.gojek.mapper.ObjectMapper;
import com.vaani.gojek.mapper.SlotStatusMapper;
import com.vaani.gojek.parking.contract.ParkingManager;
import com.vaani.gojek.parking.utils.PakingConstants;

/**
 * 
 * Implementation for managing MultiStoreyParking Instances. Design is open to introduce a persistent/third Party layer
 * . Ideally the persistence layer can be added with separate set of contract and that contract can be called from here.
 * Persistence can be anything related to storage like database,cache etc.
 * 
 * @author kchandra
 *
 */
public class MultiStoreyParkingManager implements ParkingManager {

  @Override
  public Slot findNearestEmptySlot(Parking parking) {
    MultiStoreyParking mp = (MultiStoreyParking) parking;
    Slot[] slots = mp.getSlots();
    for (int i = PakingConstants.entryPoint; i < slots.length; i++) {
      if (slots[i].isEmpty()) {
        return slots[i];
      }
    }
    return null;// means parking is full
  }

  @Override
  public void fillParkingSlot(Slot slot, Vehicle vehicle, Parking parking) {
    MultiStoreyParking mp = (MultiStoreyParking) parking;
    int slotNumber = slot.getId();
    if (slot != null) {
      slot.setEmpty(false);
      slot.setVehicle(vehicle);
    }
    Slot[] slots = mp.getSlots();
    slots[slotNumber - 1] = slot;
    mp.setSlots(slots);// not needed but as object reference will be set automatically but added for double check.

    return;

  }

  /**
   * keeping boolean for simplicity . Can be easily modified to send custom Exception or Message
   */
  @Override
  public boolean freeParkingSlot(int slotNumber, Parking parking) {
    MultiStoreyParking mp = (MultiStoreyParking) parking;
    int capacity = mp.getCapacity();
    if (slotNumber > capacity) {
      return false;
    }
    Slot[] slots = parking.getSlots();
    Slot slot = slots[slotNumber - 1];
    Vehicle v = slot.getVehicle();
    if (slot.isEmpty()) {
      // slot Number is already free so no need to free it.
      return true;
    }
    slot.setEmpty(true);
    slot.setVehicle(null);
    slots[slotNumber - 1] = slot;
    parking.setSlots(slots);// not needed but as object reference will be set automatically but added for double check.
    removeFromRegMap(mp, v);// not needed as we are already covering this while using object mapper but added for double
                            // check.
    return true;

  }

  /**
   * Added to remove a particular entry from the Map.
   * 
   * @param mp
   * @param v
   */
  private void removeFromRegMap(MultiStoreyParking mp, Vehicle v) {
   
    Map<String, ArrayList<String>> regMap = mp.getRegNumberMap();
    String color = v.getColor();
    ArrayList<String> list = regMap.get(color);
    if(list != null) {
      list.remove(v.getRegNumber());
    }
  }

  @Override
  public List<ParkingStatus> populateParkingStatus(Parking parking) {
    MultiStoreyParking mp = (MultiStoreyParking) parking;
    List<ParkingStatus> psList = new ArrayList<ParkingStatus>();
    Slot[] slots = mp.getSlots();
    for (int i = 0; i < slots.length; i++) {
      Slot slot = slots[i];
      ObjectMapper<Slot, ParkingStatus> om = new SlotStatusMapper();
      ParkingStatus ps = (ParkingStatus) om.convertObjects(slot, new ParkingStatus());
      psList.add(ps);
    }
    return psList;
  }

  @Override
  public Map<String, ArrayList<String>> populateRegMap(Vehicle vehicle, Parking parking) {
    MultiStoreyParking mp = (MultiStoreyParking) parking;
    Map<String, ArrayList<String>> regMap = mp.getRegNumberMap();
    String color = vehicle.getColor();
    String regNumber = vehicle.getRegNumber();
    ArrayList<String> regNumbers = regMap.get(color);
    if (regNumbers == null) {
      regNumbers = new ArrayList<String>();
    }
    regNumbers.add(regNumber);
    regMap.put(color, regNumbers);
    return regMap;

  }

  @Override
  public ArrayList<String> getRegistrationNumbersList(Parking parking, String color) {
    MultiStoreyParking mp = (MultiStoreyParking) parking;
    Map<String, ArrayList<String>> regMap = mp.getRegNumberMap();
    return regMap.get(color);
  }

  @Override
  public List<Integer> getSlotNumbersForColor(Parking parking, String color) {
    MultiStoreyParking mp = (MultiStoreyParking) parking;
    Slot[] slots = mp.getSlots();
    int i = 0;
    List<Integer> slotList = new ArrayList<Integer>();
    for (i = 0; i < slots.length; i++) {
      Slot s = slots[i];
      Vehicle v = s.getVehicle();
      if (v != null) {
        if (color.equalsIgnoreCase(v.getColor())) {
          // color matches add to list
          slotList.add(s.getId());
        }
      }
    }
    return slotList;
  }

  @Override
  public Integer getSlotNumbersForRegNumber(Parking parking, String regNumber) {
    MultiStoreyParking mp = (MultiStoreyParking) parking;
    Slot[] slots = mp.getSlots();
    int i = 0;
    for (i = 0; i < slots.length; i++) {
      Slot s = slots[i];
      Vehicle v = s.getVehicle();
      if (v != null) {
        if (regNumber.equalsIgnoreCase(v.getRegNumber())) {
          return s.getId();
        }
      }
    }
    return -1;
  }

}
