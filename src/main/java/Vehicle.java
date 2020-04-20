public class Vehicle {

    private Driver driver;
    private String vehicleId;
    private String name;

    public Vehicle(String id, String name, Driver driver) {
        this.vehicleId = id;
        this.name = name;
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }
}
