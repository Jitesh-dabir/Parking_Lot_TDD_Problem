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
    public void givenAVehicle_WhenParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem.park(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertEquals(true, isParked);
    }

    @Test
    public void givenAVehicle_WhenAlreadyParked_ShouldReturnFalse() throws ParkingLotException {
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.park(new Object());
            boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertEquals(false, isParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.MyException.PARKING_LOT_IS_FULL, e.type);
        }

    }

    @Test
    public void givenAVehicle_WhenUnParked_ReturnTrue() throws ParkingLotException {
        parkingLotSystem.park(vehicle);
        parkingLotSystem.unPark(vehicle);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(true, isUnParked);
    }

    @Test
    public void givenANullVehicle_WhenUnParked_ShouldThrowException() throws ParkingLotException {
        try {
            parkingLotSystem.park(vehicle);
            parkingLotSystem.unPark(null);
            boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
            Assert.assertEquals(true, isUnParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.MyException.NO_SUCH_A_VEHICLE,e.type);
        }
    }
}