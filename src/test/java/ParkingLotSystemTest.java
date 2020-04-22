import com.bridgelabz.exception.ParkingLotException;
import com.bridgelabz.observer.AirportSecurity;
import com.bridgelabz.observer.Owner;
import com.bridgelabz.service.ParkingLotSystem;
import com.bridgelabz.utility.Driver;
import com.bridgelabz.utility.ParkingLot;
import com.bridgelabz.utility.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ParkingLotSystemTest {
    LinkedHashMap<String, Vehicle> parkingLot = null;
    LinkedHashMap<String, Integer> availableLot = null;
    ParkingLotSystem parkingLotSystem = null;
    Vehicle vehicle = null;
    Owner owner = null;
    AirportSecurity airportSecurity = null;
    ParkingLot parkingLotHandler = null;
    Map<String, Vehicle> listForPoliceDepartment = null;

    @Before
    public void setUp() {
        owner = new Owner();
        airportSecurity = new AirportSecurity();
        parkingLotHandler = new ParkingLot();
        parkingLot = new LinkedHashMap();
        availableLot = new LinkedHashMap<String, Integer>();
        listForPoliceDepartment = new HashMap<>();
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        vehicle = new Vehicle("1", "car", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLACK);
        parkingLotSystem.park(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertEquals(true, isParked);
    }

    @Test
    public void givenAVehicle_WhenNotParked_ShouldReturnFalse() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        vehicle = new Vehicle("1", "car", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        Vehicle vehicle1 = new Vehicle("2", "bus", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertEquals(false, isParked);
    }

    @Test
    public void givenAVehicle_WhenUnParked_ReturnTrue() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        vehicle = new Vehicle("1", "car", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        parkingLotSystem.unPark(vehicle);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(true, isUnParked);
    }

    @Test
    public void givenANullVehicle_WhenUnParked_ShouldThrowException() {
        try {
            parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
            parkingLotSystem.createParkingLot();
            vehicle = new Vehicle("1", "car", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
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
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        vehicle = new Vehicle("1", "car", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(false, isUnParked);
    }

    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldThrowException() {
        try {
            parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
            parkingLotSystem.createParkingLot();
            int numberOfCars = 9;
            for (int i = 0; i < numberOfCars; i++) {
                Vehicle vehicle = new Vehicle(Integer.toString(i), "BMW", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
                parkingLotSystem.park(vehicle);
            }
            Vehicle vehicle = new Vehicle("11", "car", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
            Vehicle vehicle1 = new Vehicle("22", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle1);
            boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertEquals(true, isParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.MyException.PARKING_LOT_IS_FULL, e.type);
        }
    }

    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldInformToOwner() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        int numberOfCars = 9;
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), "BMW", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle = new Vehicle("11", "car", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        Vehicle vehicle1 = new Vehicle("22", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle1);
        Assert.assertEquals("Full", owner.getParkingLotStatus());
    }

    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldInformToAirportSecurityAndOwner() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        parkingLotSystem.addObserver(airportSecurity);
        int numberOfCars = 9;
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), "BMW", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle = new Vehicle("11", "car", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        Vehicle vehicle1 = new Vehicle("22", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle1);
        Assert.assertEquals("Full", airportSecurity.getParkingLotStatus());
        Assert.assertEquals("Full", owner.getParkingLotStatus());
    }

    @Test
    public void givenAVehicles_WhenParkingLotHasSpaceAgain_ShouldInformToParkingLotOwner() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        int numberOfCars = 9;
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), "BMW", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle = new Vehicle("11", "car", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        Vehicle vehicle1 = new Vehicle("22", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle1);
        Assert.assertEquals("Full", owner.getParkingLotStatus());
        parkingLotSystem.unPark(vehicle1);
        Assert.assertEquals("Have Space", owner.getParkingLotStatus());
    }

    @Test
    public void givenVehicle_WhenOwnerWantAttendant_ShouldParkTheCar() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        vehicle = new Vehicle("1", "car", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        Vehicle vehicle1 = new Vehicle("2", "car1", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle1);
        parkingLotSystem.unPark(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertEquals(true, isParked);
    }

    @Test
    public void givenVehicle_WhenWantToFindCar_ShouldNumberInParkingLot() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        int numberOfCars = 9;
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), "BMW", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle2 = new Vehicle("55", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle2);
        String numberInParkingLot = parkingLotSystem.getMyCarParkingNumber(vehicle2);
        Assert.assertEquals("C03", numberInParkingLot);
    }

    @Test
    public void givenVehicle_WhenOwnerWantAttendant_ShouldEvenlyDistributeCar() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        int numberOfCars = 8;
        Vehicle vehicle2 = new Vehicle("55", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle2);
        Vehicle vehicle3 = new Vehicle("75", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.LARGE, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle3);
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), "Toyota", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
        }
        parkingLotSystem.unPark(vehicle3);
        String numberInParkingLot = parkingLotSystem.getMyCarParkingNumber(vehicle2);
        Assert.assertEquals("A01", numberInParkingLot);
    }

    @Test
    public void givenVehicle_WhenHandicapDriverWantAttendant_ShouldGiveNearestFreeSpace() throws ParkingLotException {
        int numberOfCars = 8;
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        Vehicle vehicle2 = new Vehicle("55", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle2);
        Vehicle vehicle3 = new Vehicle("75", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.LARGE, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle3);
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), "Toyota", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
        }
        parkingLotSystem.unPark(vehicle3);
        String numberInParkingLot = parkingLotSystem.getMyCarParkingNumber(vehicle2);
        Assert.assertEquals("A01", numberInParkingLot);
    }

    @Test
    public void givenVehicle_WhenOwnerWantAttendant_ShouldDirectLargeVehicleToHighestNumberOfFreeSpace() throws ParkingLotException {
        int numberOfCars = 8;
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        Vehicle vehicle2 = new Vehicle("55", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle2);
        Vehicle vehicle3 = new Vehicle("75", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.LARGE, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle3);
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), "Toyota", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
        }
        parkingLotSystem.unPark(vehicle3);
        String numberInParkingLot = parkingLotSystem.getMyCarParkingNumber(vehicle2);
        Assert.assertEquals("A01", numberInParkingLot);
    }

    @Test
    public void givenVehicles_WhenAnySlotDoNotHaveSpaceForLargeVehicle_ShouldThrowException() throws ParkingLotException {
        try {
            int numberOfCars = 8;
            parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
            parkingLotSystem.createParkingLot();
            parkingLotSystem.addObserver(owner);
            for (int i = 0; i < numberOfCars; i++) {
                Vehicle vehicle = new Vehicle(Integer.toString(i), "Toyota", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.WHITE);
                parkingLotSystem.park(vehicle);
            }
            Vehicle vehicle2 = new Vehicle("55", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.LARGE, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle2);
            Vehicle vehicle3 = new Vehicle("75", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.LARGE, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle3);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.MyException.PARKING_LOT_IS_FULL_FOR_LARGE_VEHICLE, e.type);
        }
    }

    @Test
    public void givenVehicles_WhenPoliceDepartmentWantAllWhiteCars_ShouldReturnLocationOfAllCars() throws ParkingLotException {
        int numberOfCars = 9;
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), "Toyota", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.WHITE);
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle2 = new Vehicle("55", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle2);
        Vehicle vehicle3 = new Vehicle("75", "Thur", new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle3);
        listForPoliceDepartment = parkingLotSystem.getRecordsByVehicleColorForPolice(Vehicle.Color.WHITE);
        Assert.assertEquals(9,listForPoliceDepartment.size());
        Set<Map.Entry<String, Vehicle>> mapSet = parkingLot.entrySet();
        Map.Entry<String, Vehicle> elementAt3 = (new ArrayList<Map.Entry<String, Vehicle>>(mapSet)).get(2);
        Assert.assertEquals(Vehicle.Color.WHITE,elementAt3.getValue().getColor());
        Assert.assertEquals("A03",elementAt3.getKey());
    }
}