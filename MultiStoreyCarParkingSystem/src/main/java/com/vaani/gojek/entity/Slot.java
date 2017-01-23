package com.vaani.gojek.entity;

/**
 * Describes a parking Slot
 * This same slot can be used/extended to support different vehicle types and parking Types.
 * If there are different types of Slots (assuming current implementation/design cannot cater to that need)
 * one can add an abstract class or Interface for it and extend/implement accordingly. (Similar to Parking).
 * @author kchandra
 *
 */
public class Slot {
	
	private int id;//for maintaining the Slot Number . 
	private Vehicle vehicle;//details of Vehicle parked in the Slot.
	private boolean isEmpty;//define if a slot is empty or is a vehicle parked
	
	
	/**
	 * constructor to be called when initializing a parking lot
	 * @param isEmpty
	 */
	public Slot(int id){
		this.id = id;
		this.isEmpty = true;
	}
	
	/**
	 * Constructor to be called when parking a vehicle in this slot
	 * @param v
	 */
	public Slot(Vehicle v,int id){
		this.vehicle = v;
		this.isEmpty = false;
		this.id = id;
	}
	
	
  public Slot() {

  }

  public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}
	
	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	

	
	
	
}
