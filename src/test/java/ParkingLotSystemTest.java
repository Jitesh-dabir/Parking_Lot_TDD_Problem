import org.junit.Assert;
import org.junit.Test;

public class ParkingLotSystemTest {
    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        boolean isParked = parkingLotSystem.park(new Object());
        Assert.assertEquals(true, isParked);
    }

    @Test
    public void givenAVehicle_WhenUnParked_ReturnTrue() {
        Object vehicle =new Object();
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.park(vehicle);
        boolean isUnParked = parkingLotSystem.unPark(vehicle);
        Assert.assertEquals(true, isUnParked);
    }
}
