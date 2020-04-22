public class Vehicle {

    private final Color color;
    private Driver driver;
    private String vehicleId;
    private String name;
    private VehicleType vehicleType;

    enum VehicleType {
        SMALL, LARGE
    }

    enum Color {
        RED,BLUE,BLACK,GREEN,WHITE
    }

    public Vehicle(String id, String name, Driver driver, VehicleType vehicleType, Color color) {
        this.vehicleId = id;
        this.name = name;
        this.driver = driver;
        this.vehicleType = vehicleType;
        this.color = color;
    }

    public Driver getDriver() {
        return driver;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public VehicleType getDriverType() {
        return vehicleType;
    }
}