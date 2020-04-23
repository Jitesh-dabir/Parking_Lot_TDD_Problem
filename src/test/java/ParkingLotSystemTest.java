import com.bridgelabz.exception.ParkingLotException;
import com.bridgelabz.observer.AirportSecurity;
import com.bridgelabz.observer.Owner;
import com.bridgelabz.service.ParkingLotSystem;
import com.bridgelabz.utility.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class ParkingLotSystemTest {
    LinkedHashMap<String, Slot> parkingLot = null;
    LinkedHashMap<String, Integer> availableLot = null;
    ParkingLotSystem parkingLotSystem = null;
    Vehicle vehicle = null;
    Owner owner = null;
    AirportSecurity airportSecurity = null;
    ParkingLot parkingLotHandler = null;
    Map<String, Slot> listForPoliceDepartment = null;
    List<Attendant> attendants = null;

    @Before
    public void setUp() {
        attendants = new ArrayList<Attendant>();
        owner = new Owner(attendants);
        airportSecurity = new AirportSecurity();
        parkingLotHandler = new ParkingLot();
        parkingLot = new LinkedHashMap();
        availableLot = new LinkedHashMap<String, Integer>();
        listForPoliceDepartment = new HashMap<>();
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot(11, 4);
        vehicle = new Vehicle("1", Vehicle.Vehicle_Brand.MARUTI, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLACK);
        parkingLotSystem.park(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertEquals(true, isParked);
    }

    @Test
    public void givenAVehicle_WhenNotParked_ShouldReturnFalse() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot(11, 4);
        vehicle = new Vehicle("1", Vehicle.Vehicle_Brand.TOYOTA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        Vehicle vehicle1 = new Vehicle("2", Vehicle.Vehicle_Brand.HONDA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertEquals(false, isParked);
    }

    @Test
    public void givenAVehicle_WhenUnParked_ReturnTrue() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot(11, 4);
        vehicle = new Vehicle("1", Vehicle.Vehicle_Brand.MARUTI, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        parkingLotSystem.unPark(vehicle);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(true, isUnParked);
    }

    @Test
    public void givenANullVehicle_WhenUnParked_ShouldThrowException() {
        try {
            parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
            parkingLotSystem.createParkingLot(11, 4);
            vehicle = new Vehicle("1", Vehicle.Vehicle_Brand.CIAZ, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
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
        parkingLotSystem.createParkingLot(11, 4);
        vehicle = new Vehicle("1", Vehicle.Vehicle_Brand.HONDA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(false, isUnParked);
    }

    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldThrowException() {
        try {
            parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
            parkingLotSystem.createParkingLot(11, 4);
            int numberOfCars = 9;
            for (int i = 0; i < numberOfCars; i++) {
                Vehicle vehicle = new Vehicle(Integer.toString(i), Vehicle.Vehicle_Brand.TOYOTA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
                parkingLotSystem.park(vehicle);
            }
            Vehicle vehicle = new Vehicle("11", Vehicle.Vehicle_Brand.MARUTI, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
            Vehicle vehicle1 = new Vehicle("22", Vehicle.Vehicle_Brand.HONDA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
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
        parkingLotSystem.createParkingLot(11, 4);
        parkingLotSystem.addObserver(owner);
        int numberOfCars = 9;
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), Vehicle.Vehicle_Brand.CIAZ, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle = new Vehicle("11", Vehicle.Vehicle_Brand.MARUTI, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        Vehicle vehicle1 = new Vehicle("22", Vehicle.Vehicle_Brand.HONDA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle1);
        Assert.assertEquals("Full", owner.getParkingLotStatus());
    }

    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldInformToAirportSecurityAndOwner() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot(11, 4);
        parkingLotSystem.addObserver(owner);
        parkingLotSystem.addObserver(airportSecurity);
        int numberOfCars = 9;
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), Vehicle.Vehicle_Brand.HONDA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle = new Vehicle("11", Vehicle.Vehicle_Brand.CIAZ, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        Vehicle vehicle1 = new Vehicle("22", Vehicle.Vehicle_Brand.MARUTI, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle1);
        Assert.assertEquals("Full", airportSecurity.getParkingLotStatus());
        Assert.assertEquals("Full", owner.getParkingLotStatus());
    }

    @Test
    public void givenAVehicles_WhenParkingLotHasSpaceAgain_ShouldInformToParkingLotOwner() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot(11, 4);
        parkingLotSystem.addObserver(owner);
        int numberOfCars = 9;
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), Vehicle.Vehicle_Brand.MARUTI, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle = new Vehicle("11", Vehicle.Vehicle_Brand.BMW, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        Vehicle vehicle1 = new Vehicle("22", Vehicle.Vehicle_Brand.HONDA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle1);
        Assert.assertEquals("Full", owner.getParkingLotStatus());
        parkingLotSystem.unPark(vehicle1);
        Assert.assertEquals("Have Space", owner.getParkingLotStatus());
    }

    @Test
    public void givenVehicle_WhenOwnerWantAttendant_ShouldParkTheCar() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot(11, 4);
        parkingLotSystem.addObserver(owner);
        vehicle = new Vehicle("1", Vehicle.Vehicle_Brand.MARUTI, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle);
        Vehicle vehicle1 = new Vehicle("2", Vehicle.Vehicle_Brand.HONDA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle1);
        parkingLotSystem.unPark(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertEquals(true, isParked);
    }

    @Test
    public void givenVehicle_WhenWantToFindCar_ShouldNumberInParkingLot() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot(11, 4);
        parkingLotSystem.addObserver(owner);
        int numberOfCars = 9;
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), Vehicle.Vehicle_Brand.CIAZ, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle2 = new Vehicle("55", Vehicle.Vehicle_Brand.HONDA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle2);
        String numberInParkingLot = parkingLotSystem.getMyCarParkingNumber(vehicle2);
        Assert.assertEquals("C03", numberInParkingLot);
    }

    @Test
    public void givenVehicle_WhenOwnerWantAttendant_ShouldEvenlyDistributeCar() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot(11, 4);
        parkingLotSystem.addObserver(owner);
        int numberOfCars = 8;
        Vehicle vehicle2 = new Vehicle("55", Vehicle.Vehicle_Brand.MARUTI, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle2);
        Vehicle vehicle3 = new Vehicle("75", Vehicle.Vehicle_Brand.CIAZ, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.LARGE, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle3);
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), Vehicle.Vehicle_Brand.BMW, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
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
        parkingLotSystem.createParkingLot(11, 4);
        parkingLotSystem.addObserver(owner);
        Vehicle vehicle2 = new Vehicle("55", Vehicle.Vehicle_Brand.MARUTI, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle2);
        Vehicle vehicle3 = new Vehicle("75", Vehicle.Vehicle_Brand.CIAZ, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.LARGE, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle3);
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), Vehicle.Vehicle_Brand.BMW, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
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
        parkingLotSystem.createParkingLot(11, 4);
        parkingLotSystem.addObserver(owner);
        Vehicle vehicle2 = new Vehicle("55", Vehicle.Vehicle_Brand.MARUTI, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle2);
        Vehicle vehicle3 = new Vehicle("75", Vehicle.Vehicle_Brand.CIAZ, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.LARGE, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle3);
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle(Integer.toString(i), Vehicle.Vehicle_Brand.BMW, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
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
            parkingLotSystem.createParkingLot(11, 4);
            parkingLotSystem.addObserver(owner);
            for (int i = 0; i < numberOfCars; i++) {
                Vehicle vehicle = new Vehicle("MH46-" + Integer.toString(i), Vehicle.Vehicle_Brand.TOYOTA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
                parkingLotSystem.park(vehicle);
            }
            Vehicle vehicle2 = new Vehicle("MH46-55", Vehicle.Vehicle_Brand.HONDA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle2);
            Vehicle vehicle3 = new Vehicle("MH46-75", Vehicle.Vehicle_Brand.TOYOTA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle3);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.MyException.PARKING_LOT_IS_FULL_FOR_LARGE_VEHICLE, e.type);
        }
    }

    @Test
    public void givenVehicles_WhenPoliceDepartmentWantAllWhiteCars_ShouldReturnLocationOfAllCars() throws ParkingLotException {
        int numberOfCars = 9;
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot(11, 4);
        parkingLotSystem.addObserver(owner);
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle("MH46-" + Integer.toString(i), Vehicle.Vehicle_Brand.TOYOTA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.WHITE);
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle2 = new Vehicle("MH46-55", Vehicle.Vehicle_Brand.HONDA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle2);
        Vehicle vehicle3 = new Vehicle("MH46-75", Vehicle.Vehicle_Brand.TOYOTA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle3);
        listForPoliceDepartment = parkingLotSystem.getRecordsByVehicleColorForPolice(Vehicle.Color.WHITE);
        Assert.assertEquals(9, listForPoliceDepartment.size());
        Set<Map.Entry<String, Slot>> mapSet = parkingLot.entrySet();
        Map.Entry<String, Slot> elementAt3 = (new ArrayList<Map.Entry<String, Slot>>(mapSet)).get(2);
        Assert.assertEquals(Vehicle.Color.WHITE, elementAt3.getValue().getVehicle().getColor());
        Assert.assertEquals("A03", elementAt3.getKey());
    }

    @Test
    public void givenVehicles_WhenPoliceDepartmentWantAllBlueToyotaCarsInformation_ShouldReturnLocationOfAllCars() throws ParkingLotException {
        int numberOfCars = 9;
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot(11, 4);
        parkingLotSystem.addObserver(owner);
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle("MH46-" + Integer.toString(i), Vehicle.Vehicle_Brand.TOYOTA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle2 = new Vehicle("MH46-55", Vehicle.Vehicle_Brand.HONDA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle2);
        Vehicle vehicle3 = new Vehicle("MH46-75", Vehicle.Vehicle_Brand.TOYOTA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle3);
        listForPoliceDepartment = parkingLotSystem.getRecordsByVehicleColorAndName(Vehicle.Vehicle_Brand.TOYOTA, Vehicle.Color.BLUE);
        Assert.assertEquals(10, listForPoliceDepartment.size());
        Set<Map.Entry<String, Slot>> mapSet = parkingLot.entrySet();
        Map.Entry<String, Slot> elementAt3 = (new ArrayList<Map.Entry<String, Slot>>(mapSet)).get(2);
        Assert.assertEquals(Vehicle.Color.BLUE, elementAt3.getValue().getVehicle().getColor());
        Assert.assertEquals("A03", elementAt3.getKey());
        Assert.assertEquals("MH46-7", elementAt3.getValue().getVehicle().getVehicleId());
        Assert.assertEquals("attendant_A", elementAt3.getValue().getAttendant().getName());
    }

    @Test
    public void givenVehicles_WhenPoliceDepartmentWantBMWCarsInformation_ShouldReturnLocationOfAllCars() throws ParkingLotException {
        int numberOfCars = 9;
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot(11, 4);
        parkingLotSystem.addObserver(owner);
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle("MH46-" + Integer.toString(i), Vehicle.Vehicle_Brand.BMW, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.RED);
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle2 = new Vehicle("MH46-55", Vehicle.Vehicle_Brand.HONDA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle2);
        Vehicle vehicle3 = new Vehicle("MH46-75", Vehicle.Vehicle_Brand.TOYOTA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle3);
        listForPoliceDepartment = parkingLotSystem.getRecordsByCarBrand(Vehicle.Vehicle_Brand.BMW);
        Assert.assertEquals(9, listForPoliceDepartment.size());
        Set<Map.Entry<String, Slot>> mapSet = parkingLot.entrySet();
        Map.Entry<String, Slot> elementAt3 = (new ArrayList<Map.Entry<String, Slot>>(mapSet)).get(2);
        Assert.assertEquals(11, mapSet.size());
        Assert.assertEquals(Vehicle.Color.RED, elementAt3.getValue().getVehicle().getColor());
        Assert.assertEquals("A03", elementAt3.getKey());
        Assert.assertEquals("MH46-7", elementAt3.getValue().getVehicle().getVehicleId());
        Assert.assertEquals("attendant_A", elementAt3.getValue().getAttendant().getName());
    }

    @Test
    public void givenVehicles_WhenPoliceDepartmentWantCarsHaveBeenParkedInLast30Minutes_ShouldReturnLocationOfAllCars() throws ParkingLotException {
        int numberOfCars = 9;
        parkingLotSystem = new ParkingLotSystem(owner, parkingLotHandler, parkingLot, availableLot);
        parkingLotSystem.createParkingLot(11, 4);
        parkingLotSystem.addObserver(owner);
        for (int i = 0; i < numberOfCars; i++) {
            Vehicle vehicle = new Vehicle("MH46-" + Integer.toString(i), Vehicle.Vehicle_Brand.BMW, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.RED);
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle2 = new Vehicle("MH46-55", Vehicle.Vehicle_Brand.HONDA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle2);
        Vehicle vehicle3 = new Vehicle("MH46-75", Vehicle.Vehicle_Brand.TOYOTA, new Driver(Driver.DriverType.NORMAL), Vehicle.VehicleType.SMALL, Vehicle.Color.BLUE);
        parkingLotSystem.park(vehicle3);
        listForPoliceDepartment = parkingLotSystem.getRecordsByTime();
        Assert.assertEquals(11, listForPoliceDepartment.size());
        Set<Map.Entry<String, Slot>> mapSet = listForPoliceDepartment.entrySet();
        Map.Entry<String, Slot> elementAt3 = (new ArrayList<Map.Entry<String, Slot>>(mapSet)).get(2);
        Assert.assertEquals(11, mapSet.size());
        Assert.assertEquals(Vehicle.Color.RED, elementAt3.getValue().getVehicle().getColor());
        Assert.assertEquals("B01", elementAt3.getKey());
        Assert.assertEquals("MH46-1", elementAt3.getValue().getVehicle().getVehicleId());
        Assert.assertEquals("attendant_B", elementAt3.getValue().getAttendant().getName());
    }
}