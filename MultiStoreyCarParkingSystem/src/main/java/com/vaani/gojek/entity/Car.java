package com.vaani.gojek.entity;

import com.vaani.gojek.entity.type.VehicleType;

public class Car extends Vehicle {
	public Car(String regNumber, String color) {
		super(regNumber, color);
		this.vType = VehicleType.Car;
	}
}
