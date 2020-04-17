public class Vehicle {
    private String vehicleId;
    private String name;

    public Vehicle(String id, String name) {
        this.vehicleId = id;
        this.name = name;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getName() {
        return name;
    }
}
