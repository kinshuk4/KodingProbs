package com.vaani.gojek.mapper;

import com.vaani.gojek.entity.ParkingStatus;
import com.vaani.gojek.entity.Slot;
import com.vaani.gojek.entity.Vehicle;

/**
 * Mapper to getParkingStatus object from slot.
 * @author kchandra
 *
 */
public class SlotStatusMapper implements ObjectMapper<Slot,ParkingStatus> {

	
	@Override
	public Object convertObjects(Object o1, Object o2) {
		Slot s = (Slot)o1;
		ParkingStatus ps = (ParkingStatus)o2;
		Vehicle v = s.getVehicle();//s cant be null in current implementation. Avoided if to avoid cluttering
		if(v!=null){
			ps.setColor(v.getColor());
			ps.setRegNumber(v.getRegNumber());
		}
		ps.setSlotNo(s.getId());
		return ps;
	}

}
