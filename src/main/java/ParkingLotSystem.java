public class ParkingLotSystem {

    private Object vehicle = null;

    public ParkingLotSystem() {
    }

    public boolean park(Object vehicle) {
        if (this.vehicle != null)
            return false;
        this.vehicle = vehicle;
        return true;
    }

    public boolean unPark(Object vehicle) {
        if (this.vehicle.equals(vehicle))
            return true;
        return false;
    }
}