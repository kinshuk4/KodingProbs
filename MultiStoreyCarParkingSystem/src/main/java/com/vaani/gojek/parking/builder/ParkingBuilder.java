package com.vaani.gojek.parking.builder;

import com.vaani.gojek.entity.MultiStoreyParking;
import com.vaani.gojek.entity.Parking;

/**
 * Builder class to build different types of Parking Space.
 * For assignment only MultiStoreyParkingSpace has been created.
 * Can add overloaded or different methods for creating different
 * type of parking space
 * @author kchandra
 *
 */
public class ParkingBuilder {
	
	public static Parking buildParkingLot(int n){
		Parking p = null;
		if(n <=0){
			return p;
		}
		p = new MultiStoreyParking(n);
		return p;
	}

}
