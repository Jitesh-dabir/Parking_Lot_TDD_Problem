public class Vehicle {

    enum VehicleType {
        SMALL, LARGE
    }

    private Driver driver;
    private String vehicleId;
    private String name;
    private VehicleType vehicleType;

    public Vehicle(String id, String name, Driver driver, VehicleType vehicleType) {
        this.vehicleId = id;
        this.name = name;
        this.driver = driver;
        this.vehicleType = vehicleType;
    }

    public Driver getDriver() {
        return driver;
    }

    public VehicleType getDriverType() {
        return vehicleType;
    }
}