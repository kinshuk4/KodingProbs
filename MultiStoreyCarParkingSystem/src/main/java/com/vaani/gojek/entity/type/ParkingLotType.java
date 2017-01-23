package com.vaani.gojek.entity.type;

/**
 * Enum to support Different types of Parking Lot.
 * Current Problem statement appears to support Single Floor parking. 
 * Although the problem statement says MultiStorey, example inputs suggest otherwise.
 * @author kchandra
 *
 */
public enum ParkingLotType {

	SingleFloor("singleFloor"),
	MultiFloor("multiFloor");
	
	private String value;
	
	private ParkingLotType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
