package gojek.test.mocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaani.gojek.entity.Car;
import com.vaani.gojek.entity.MultiStoreyParking;
import com.vaani.gojek.entity.Parking;
import com.vaani.gojek.entity.ParkingStatus;
import com.vaani.gojek.entity.Slot;
import com.vaani.gojek.entity.Vehicle;

/**
 * Class for Mocking features which need to be consumed by other tests. In actual projects , some mocking frameworks can
 * be used. Writing my own implementations here for simplicity and avoid third party library usage.
 * 
 * @author kchandra
 *
 */
public class MockParkingFeatures {

  private List<ParkingStatus> psList;
  private List<Integer> mockSlotListWhite;
  private List<Integer> mockSlotListBlack;
  private Map<String, ArrayList<String>> mockRegMap;

  public Parking mockMultiStoreyParkingWithFilledSlots() {
    Vehicle v1 = new Car("KA-­01-­HH-­1234", "White");
    Vehicle v2 = new Car("KA­-01­-HH­-9999", "Black");
    Vehicle v3 = new Car("KA-02-BB-1249", "White");
    MultiStoreyParking p = new MultiStoreyParking(3);
    Slot[] s = p.getSlots();
    s[0] = new Slot(v1, 1);
    s[1] = new Slot(v2, 2);
    s[2] = new Slot(v3, 3);
    psList = new ArrayList<ParkingStatus>();
    populateMockSlotListWhite();
    populateMockSlotListBlack();
    populateMockRegMap(p);
    p.setRegNumberMap(mockRegMap);
    populateMockParkingStatusList(psList, s);

    return p;
  }

  private void populateMockRegMap(MultiStoreyParking p) {
    mockRegMap = new HashMap<String, ArrayList<String>>();
    ArrayList<String> whiteList = new ArrayList<String>();
    ArrayList<String> blackList = new ArrayList<String>();
    whiteList.add("KA­-01­-HH­-1234");
    whiteList.add("KA-02-BB-1249");
    blackList.add("KA­-01­-HH­-9999");
    mockRegMap.put("White", whiteList);
    mockRegMap.put("Black", blackList);
    this.setMockRegMap(mockRegMap);

  }

  private void populateMockParkingStatusList(List<ParkingStatus> psList, Slot[] slots) {
    int len = slots.length;
    for (int i = 0; i < len; i++) {
      Slot s = slots[i];
      Vehicle v = s.getVehicle();
      ParkingStatus ps = new ParkingStatus(s.getId(), v.getRegNumber(), v.getColor());
      psList.add(ps);
    }
    setPsList(psList);
  }

  public List<ParkingStatus> getPsList() {
    return psList;
  }

  public void setPsList(List<ParkingStatus> psList) {
    this.psList = psList;
  }

  public List<Integer> getMockSlotListWhite() {
    return mockSlotListWhite;
  }

  public void populateMockSlotListWhite() {
    this.mockSlotListWhite = new ArrayList<Integer>();
    mockSlotListWhite.add(1);
    mockSlotListWhite.add(3);

  }

  public List<Integer> getMockSlotListBlack() {
    return mockSlotListBlack;
  }

  public void populateMockSlotListBlack() {
    this.mockSlotListBlack = new ArrayList<Integer>();
    mockSlotListBlack.add(2);
  }

  public Map<String, ArrayList<String>> getMockRegMap() {
    return mockRegMap;
  }

  public void setMockRegMap(Map<String, ArrayList<String>> mockRegMap) {
    this.mockRegMap = mockRegMap;
  }

}
