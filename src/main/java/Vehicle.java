public class Vehicle {

    enum DriverType {
        HANDICAP,NORMAL
    }
    private String vehicleId;
    private String name;
    private DriverType driverType;

    public Vehicle(String id, String name, DriverType driverType) {
        this.vehicleId = id;
        this.name = name;
        this.driverType=driverType;
    }

    public DriverType getDriverType() {
        return driverType;
    }
}
