import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {
    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        boolean isParked = parkingLotSystem.park(new Object());
        Assert.assertEquals(true, isParked);
    }
}
