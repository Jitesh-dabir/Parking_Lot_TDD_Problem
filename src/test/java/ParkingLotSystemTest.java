import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;

public class ParkingLotSystemTest {
    LinkedHashMap<String, Vehicle> parkingLot = null;
    ParkingLotSystem parkingLotSystem = null;
    Vehicle vehicle = null;
    Owner owner = null;
    AirportSecurity airportSecurity = null;
    Attendant attendant = null;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem();
        owner = new Owner();
        airportSecurity = new AirportSecurity();
        attendant = new Attendant();
        parkingLot = new LinkedHashMap();
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, attendant,parkingLot);
        parkingLotSystem.createParkingLot();
        vehicle = new Vehicle("1", "car");
        parkingLotSystem.park(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertEquals(true, isParked);
    }

    @Test
    public void givenAVehicle_WhenNotParked_ShouldReturnFalse() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, attendant,parkingLot);
        parkingLotSystem.createParkingLot();
        vehicle = new Vehicle("1", "car");
        Vehicle vehicle1 = new Vehicle("2", "bus");
        parkingLotSystem.park(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertEquals(false, isParked);
    }

    @Test
    public void givenAVehicle_WhenUnParked_ReturnTrue() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, attendant,parkingLot);
        parkingLotSystem.createParkingLot();
        vehicle = new Vehicle("1", "car");
        parkingLotSystem.park(vehicle);
        parkingLotSystem.unPark(vehicle);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(true, isUnParked);
    }

    @Test
    public void givenANullVehicle_WhenUnParked_ShouldThrowException() {
        try {
            parkingLotSystem = new ParkingLotSystem(owner, attendant,parkingLot);
            parkingLotSystem.createParkingLot();
            vehicle = new Vehicle("1", "car");
            parkingLotSystem.park(vehicle);
            parkingLotSystem.unPark(null);
            boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
            Assert.assertEquals(true, isUnParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.MyException.NO_SUCH_A_VEHICLE, e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParkedAndCheckIfUnPark_ShouldReturnFalse() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, attendant,parkingLot);
        parkingLotSystem.createParkingLot();
        vehicle = new Vehicle("1", "car");
        parkingLotSystem.park(vehicle);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(false, isUnParked);
    }

    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldThrowException() {
        try {
            parkingLotSystem = new ParkingLotSystem(owner, attendant,parkingLot);
            parkingLotSystem.createParkingLot();
            int numberOfCars = 9;
            for (int i = 0; i < numberOfCars; i++) {
                Vehicle vehicle = new Vehicle(Integer.toString(i), "BMW");
                parkingLotSystem.park(vehicle);
            }
            vehicle = new Vehicle("11", "car");
            parkingLotSystem.park(vehicle);
            Vehicle vehicle1 = new Vehicle("12", "car1");
            parkingLotSystem.park(vehicle1);
            Vehicle vehicle2 = new Vehicle("13", "car2");
            parkingLotSystem.park(vehicle2);
            boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertEquals(true, isParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.MyException.PARKING_LOT_IS_FULL, e.type);
        }
    }

    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldInformToOwner() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, attendant,parkingLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        int numberOfCars = 9;
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), "BMW");
            parkingLotSystem.park(vehicle);
        }
        vehicle = new Vehicle("11", "car");
        parkingLotSystem.park(vehicle);
        Vehicle vehicle1 = new Vehicle("12", "Thur");
        parkingLotSystem.park(vehicle1);
        Assert.assertEquals("Full", owner.getParkingLotStatus());
    }

    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldInformToAirportSecurityAndOwner() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, attendant,parkingLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        parkingLotSystem.addObserver(airportSecurity);
        int numberOfCars = 9;
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), "BMW");
            parkingLotSystem.park(vehicle);
        }
        vehicle = new Vehicle("11", "BMW");
        parkingLotSystem.park(vehicle);
        Vehicle vehicle1 = new Vehicle("12", "Thur");
        parkingLotSystem.park(vehicle1);
        Assert.assertEquals("Full", airportSecurity.getParkingLotStatus());
        Assert.assertEquals("Full", owner.getParkingLotStatus());
    }

    @Test
    public void givenAVehicles_WhenParkingLotHasSpaceAgain_ShouldInformToParkingLotOwner() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, attendant,parkingLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        int numberOfCars = 9;
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), "BMW");
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle = new Vehicle("11", "car");
        parkingLotSystem.park(vehicle);
        Vehicle vehicle1 = new Vehicle("22", "Thur");
        parkingLotSystem.park(vehicle1);
        Assert.assertEquals("Full", owner.getParkingLotStatus());
        parkingLotSystem.unPark(vehicle1);
        Assert.assertEquals("Have Space", owner.getParkingLotStatus());
    }

    @Test
    public void givenVehicle_WhenOwnerWantAttendant_ShouldParkTheCar() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, attendant,parkingLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        vehicle = new Vehicle("1", "car");
        parkingLotSystem.park(vehicle);
        Vehicle vehicle1 = new Vehicle("2", "car1");
        parkingLotSystem.park(vehicle1);
        parkingLotSystem.unPark(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertEquals(true, isParked);
    }

    @Test
    public void givenVehicle_WhenWantToFindCar_ShouldNumberInParkingLot() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, attendant,parkingLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        int numberOfCars = 9;
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), "BMW");
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle2 = new Vehicle("55", "Thur");
        parkingLotSystem.park(vehicle2);
        int numberInParkingLot = parkingLotSystem.getMyCarParkingNumber(vehicle2);
        Assert.assertEquals(9, numberInParkingLot);
    }
}