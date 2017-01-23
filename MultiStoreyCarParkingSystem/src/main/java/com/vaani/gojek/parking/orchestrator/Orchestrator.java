package com.vaani.gojek.parking.orchestrator;

import java.util.ArrayList;
import java.util.List;

import com.vaani.gojek.entity.Parking;
import com.vaani.gojek.entity.ParkingStatus;
import com.vaani.gojek.entity.Slot;
import com.vaani.gojek.entity.Vehicle;
import com.vaani.gojek.entity.type.VehicleType;
import com.vaani.gojek.parking.builder.ParkingResponseBuilder;
import com.vaani.gojek.parking.builder.ResponseBuilder;
import com.vaani.gojek.parking.contract.GoJekParking;
import com.vaani.gojek.parking.contract.impl.GoJekParkingImpl;
import com.vaani.gojek.parking.exceptions.GoJekException;
import com.vaani.gojek.vehicle.factory.VehicleFactory;
import com.vaani.gojek.parking.utils.ResponseMessages;

/**
 * 
 * Orchestrator class to convert/call the ParkingManager API's for both command Line and File Based Input
 * Since the implementation is similar for both File based and Command Based Input , so 
 * logic has been moved here. If there is any dependency that needs to be created to
 * call the main Library , this layer constructs that . There is no real business logic here.
 * ThreadSafety - Assuming multiple instances of Parking Lot can be created I am not making it thread safe.
 * Alternatively , one can wrap it inside a singleton if only one instance needs to be created.
 * @author kchandra
 *
 */
public class Orchestrator {

  private GoJekParking gjParking;
  private Parking p;
  protected ResponseBuilder rb;
  
  public Orchestrator() {
    this.gjParking = new GoJekParkingImpl();
    this.rb = new ParkingResponseBuilder();
  }
  
  public Orchestrator(Parking p) {
    this.gjParking = new GoJekParkingImpl();
    this.rb = new ParkingResponseBuilder();
    this.p = p;
  }
  
  
  public String createParkingLot(int n) {
    String resp = null;
    try {
      p = gjParking.createParkingLot(n);
      setParking(p);
      resp = ResponseMessages.pakingCreationSuccess + String.valueOf(n) + ResponseMessages.slot;
    } catch (GoJekException e) {
      resp = ResponseMessages.parkingCreationFailure;
    }
    rb.buildResponse(resp);
    return resp;
  }

  public String ParkVehicle(String regNumber, String color, VehicleType vType) {
    VehicleFactory vFactory = new VehicleFactory();
    String resp = null;
    try {
      Vehicle v = vFactory.createVehicle(regNumber, color, vType);
      Parking p = getParking();
      Slot slot = gjParking.ParkVehicle(p, v);
      if (slot == null) {
        resp = ResponseMessages.parkingLotFull;
      } else {
        resp = ResponseMessages.parkingAllocated + String.valueOf(slot.getId());
      }
      rb.buildResponse(resp);
    } catch (GoJekException ge) {
      // building response for error message . Error response written is
      // defined by me as assignment
      // does not talk about dealing this case.
      resp = ge.getMessage();
    }
    return resp;
  }

  public String freeSlot(int slotId) {
    Parking p = getParking();
    String resp = null;
    if (p == null) {
      resp = ResponseMessages.parkingNotPresent;
      rb.buildResponse(resp);
      return resp;
    }
    try {
      gjParking.freeParkingSlot(p, slotId);
      resp = ResponseMessages.slotNumber + slotId + ResponseMessages.isFree;
    } catch (GoJekException e) {
      resp = e.getMessage();
    }
    rb.buildResponse(resp);
    return resp;
  }

  public void getParkingStatus() {
    p = getParking();
    try {
      List<ParkingStatus> psList = gjParking.getStatusforParking(p);
      rb.buildTabDelimiterResponse(psList);
    } catch (GoJekException e) {
      String resp = e.getMessage();
      rb.buildResponse(resp);
    }
    return;
    
  }

  public void getRegistrationNumbers(String color) {
    p = getParking();
    String resp = null;
    ArrayList<String> regNumbers = null;
    try {
      regNumbers = gjParking.getRegistrationNumbers(p, color);
      rb.buildCommaSeperatedResponse(regNumbers);
    } catch (GoJekException e) {
      resp = e.getMessage();
      rb.buildResponse(resp);
    }
    return;
    
  }

  public void getSlotNumbersForColor(String color) {
    p = getParking();
    String resp = null;
    try {
      List<Integer> slotList = gjParking.getSlotNumbersForColor(p, color);
      if (slotList.size() == 0) {
        rb.buildResponse(ResponseMessages.notFound);
        return;
      }
      rb.buildCommaSeperatedResponse(slotList);
    } catch (GoJekException e) {
      resp = e.getMessage();
      rb.buildResponse(resp);
    }
    return;
    
  }

  public void getSlotNumbersForRegistrationNumbers(String regNumber) {
    p = getParking();
    try {
      Integer slotNumber = gjParking.getSlotNumberForRegistrationNumber(p, regNumber);
      if (slotNumber  ==  -1) {
        rb.buildResponse(ResponseMessages.notFound);
        return;
      }
     rb.buildResponse(slotNumber.toString());
    } catch (GoJekException e) {
      String resp = e.getMessage();
      rb.buildResponse(resp);
    }
    return;

    
  }

  /**
   * getter Method for Parking Object
   * 
   * @return
   */
  public Parking getParking() {
    return p;
  }

  /**
   * setter Method for Parking Object
   * 
   * @return
   */
  public void setParking(Parking p) {
    this.p = p;
  }
}
