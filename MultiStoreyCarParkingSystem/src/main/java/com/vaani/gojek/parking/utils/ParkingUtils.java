package com.vaani.gojek.parking.utils;

import com.vaani.gojek.entity.Parking;
import com.vaani.gojek.entity.type.ParkingLotType;
import com.vaani.gojek.parking.contract.ParkingManager;
import com.vaani.gojek.parking.contract.impl.MultiStoreyParkingManager;
import com.vaani.gojek.parking.exceptions.GoJekException;

/**
 * 
 * @author kchandra
 */
public class ParkingUtils {

	public static ParkingManager getParkingType(Parking p) throws GoJekException {
		ParkingLotType pType = p.getpType();
		switch (pType) {
		case SingleFloor:
			return new MultiStoreyParkingManager();
			// can add for other Parking types here
		default:
			throw new GoJekException("Not a valid Parking Lot Type");
		}
		
	}

	
}
