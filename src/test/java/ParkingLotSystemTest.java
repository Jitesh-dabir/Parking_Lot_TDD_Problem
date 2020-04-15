import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {
    Object vehicle = null;
    ParkingLotSystem parkingLotSystem = null;

    @Before
    public void setUp() throws Exception {
        vehicle = new Object();
        parkingLotSystem = new ParkingLotSystem();
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        boolean isParked = parkingLotSystem.park(new Object());
        Assert.assertEquals(true, isParked);
    }

    @Test
    public void givenAVehicle_WhenUnParked_ReturnTrue() {
        parkingLotSystem.park(vehicle);
        boolean isUnParked = parkingLotSystem.unPark(vehicle);
        Assert.assertEquals(true, isUnParked);
    }
}
