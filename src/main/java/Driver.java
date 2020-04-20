public class Driver {

    enum DriverType {
        HANDICAP,NORMAL
    }

    private DriverType driverType;

    public Driver(DriverType driverType) {
        this.driverType=driverType;
    }

    public DriverType getDriverType() {
        return driverType;
    }
}
