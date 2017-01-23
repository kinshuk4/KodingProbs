package com.vaani.gojek.entity.type;

public enum VehicleType {
	
	
	Car("Car"),
	Truck("Truck"),
	TwoWheeler("TwoWheeler");
	
	private String value;
	
	private VehicleType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
