package com.vaani.gojek.entity;

import com.vaani.gojek.entity.type.ParkingLotType;

/**
 * Abstract Class/Contract to define a Parking Lot.
 * As per problem Statement , the current Implementation requires Single Dimensional single Floor Parking
 * Space . But In future , it can be extended to support various types of parking Systems (multi floor, multi dimensional,
 * multivehicle type).
 * @author kchandra
 *
 */
public abstract class Parking {

	private ParkingLotType pType;
	
	
	public Parking(ParkingLotType pType){
		this.pType = pType;
		
	}

	public ParkingLotType getpType() {
		return pType;
	}


	public void setpType(ParkingLotType pType) {
		this.pType = pType;
	}
	
	public abstract Slot[] getSlots() ;

	public abstract void setSlots(Slot[] slots) ;

}
