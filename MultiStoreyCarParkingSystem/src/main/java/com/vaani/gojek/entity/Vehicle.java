package com.vaani.gojek.entity;

import com.vaani.gojek.entity.type.VehicleType;


/**
 * Abstract  to declare Vehicle and encapsulate the common behavior shared by all Vehicles.
 * Currently only Car extends it but
 * if needed different type of Vehicle can be supported.
 * An abstract class defining Type (Two Wheeler , 4Wheeler or Heavyweight (for trucks and lorries) and this can 
 * be changed to an Interface .An Enum has been introduced to describe the vehicle . For now , the problem statement 
 * only asks to provide implementation for Car. So providing implementation for same.
 * This design makes the existing implementation open for extension but closed for modification(Open-Closed Principle)
 * @author kchandra
 *
 */
public abstract class Vehicle {
	
	private String regNumber;
		
	private String color;
	
	protected VehicleType vType;
	
	public Vehicle(String regNumber, String color) {
		super();
		this.regNumber = regNumber;
		this.color = color;
	}
	
	
	public Vehicle(String regNumber, String color,VehicleType vType) {
		super();
		this.regNumber = regNumber;
		this.color = color;
		this.vType = vType;
	}


	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public VehicleType getvType() {
		return vType;
	}

	public void setvType(VehicleType vType) {
		this.vType = vType;
	}
	
	
	
	

}
