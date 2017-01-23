package com.vaani.gojek.entity;


/**
 * Class to represent Parking Status
 * which will be send back as response to status Query
 * @author kchandra
 *
 */
public class ParkingStatus{
	
	
	private int slotNo;
	
	private String regNumber;
	
	private String color;

	
	public ParkingStatus(){
		
	}
	
	
	public ParkingStatus(int slotNo, String regNumber, String color) {
		super();
		this.slotNo = slotNo;
		this.regNumber = regNumber;
		this.color = color;
	}

	public int getSlotNo() {
		return slotNo;
	}

	public void setSlotNo(int slotNo) {
		this.slotNo = slotNo;
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
	
	

	public String toStringResponse() {
		return slotNo + "\t" + regNumber + "\t" + color;
				
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result
				+ ((regNumber == null) ? 0 : regNumber.hashCode());
		result = prime * result + slotNo;
		return result;
	}


	/**
	 * Overriden for unit Tests
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParkingStatus other = (ParkingStatus) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (regNumber == null) {
			if (other.regNumber != null)
				return false;
		} else if (!regNumber.equals(other.regNumber))
			return false;
		if (slotNo != other.slotNo)
			return false;
		return true;
	}	
	

}
