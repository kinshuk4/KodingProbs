package com.vaani.gojek.vehicle.factory;

import com.vaani.gojek.entity.Car;
import com.vaani.gojek.entity.Vehicle;
import com.vaani.gojek.entity.type.VehicleType;
import com.vaani.gojek.parking.exceptions.GoJekException;

/**
 * 
 * Factory class to build different types of Vehicle like car , truck , two wheeler etc.
 * This has been introduced to provide decoupling
 * between Parking and Vehicle Management.
 * @author kchandra
 *
 */
public class VehicleFactory {


	/**
	 * 
	 * @param regNumber
	 * @param color
	 * @return
	 * @throws GoJekException 
	 */
	public Vehicle createVehicle(String regNumber, String color,VehicleType vt) throws GoJekException {
		if(vt.equals(VehicleType.Car)){
			return new Car(regNumber,color);
		}else{
			throw new GoJekException("This vehicle Type  is supported for parking");
		}
	}
}
