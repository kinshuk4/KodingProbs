package gojek.test;

import static gojek.test.constants.TestResponseStrings.color;
import static gojek.test.constants.TestResponseStrings.validRegNo;
import static gojek.test.constants.TestResponseStrings.color2;
import static gojek.test.constants.TestResponseStrings.validRegNo2;

import gojek.test.mocks.MockParkingFeatures;

import org.junit.BeforeClass;

import com.vaani.gojek.entity.Car;
import com.vaani.gojek.entity.Parking;
import com.vaani.gojek.entity.Vehicle;
import com.vaani.gojek.parking.contract.GoJekParking;
import com.vaani.gojek.parking.contract.impl.GoJekParkingImpl;
import com.vaani.gojek.parking.exceptions.GoJekException;
import com.vaani.gojek.parking.orchestrator.Orchestrator;

/**
 * 
 * @author kchandra
 *
 */
public abstract class AbstractTest {

	protected static GoJekParking gjParking;
	protected static Parking park; // to test apis which expect a parking lot created						
	protected static Orchestrator or;
	protected static Vehicle validCar;
	protected static Vehicle validCar2;
	protected static MockParkingFeatures mp;

	@BeforeClass
	public static void setUpBeforeClass() {
		gjParking = new GoJekParkingImpl();
		or = new Orchestrator();
		mp = new MockParkingFeatures();
		validCar = new Car(validRegNo, color);
		validCar2 = new Car(validRegNo2,color2);
		try {
			park = gjParking.createParkingLot(5);// to be used by other tests
		} catch (GoJekException e) {
			// doing sysout . In actual better to use logging.
			System.out
					.println("Parking lot could not be initialized . Please check");
		}
		or.setParking(park);
	}

}
